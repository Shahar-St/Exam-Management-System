package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.ExecuteExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.ExecuteExamResponse;

import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class ExecuteExamStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client,
                                   Session session, List<String> loggedInUsers) {

        ExecuteExamRequest request1 = (ExecuteExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ExecuteExamResponse(UNAUTHORIZED, request);

        questionsAndExamsLock.lock();

        Exam exam = getTypeById(Exam.class, request1.getExamID(), session);
        if (exam == null)
        {
            questionsAndExamsLock.unlock();
            return new ExecuteExamResponse(ERROR2, request);
        }

        List<Student> students = exam.getCourse().getStudentsList();

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        ConcreteExam concreteExam = new ConcreteExam(exam, teacher, request1.getExamCode());
        concreteExam.setExamForExecutionInitDate(LocalDateTime.now());
        session.saveOrUpdate(concreteExam);
        session.flush();

        for (Student student : students)
        {
            ExecutedExam executedExam = new ExecutedExam(concreteExam, student);
            session.save(executedExam);
            student.setCurrentlyExecutedID(executedExam.getId());
            session.update(executedExam);
        }
        session.flush();

        questionsAndExamsLock.unlock();
        return new ExecuteExamResponse(SUCCESS, request, String.valueOf(concreteExam.getId()), concreteExam.getExam().getDurationInMinutes());
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers, ConnectionToClient client, Session session) {

        ExecuteExamResponse response1 = (ExecuteExamResponse) response;

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, response1.getConcreteExamID(), session);
        examManagers.put(concreteExam.getId(), new ExamManager(client));
    }
}
