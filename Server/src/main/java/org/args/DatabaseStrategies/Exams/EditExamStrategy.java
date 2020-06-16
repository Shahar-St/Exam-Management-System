package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.EditExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.EditExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 * 3 - trying to delete an exam that wasn't written by the user
 * 4 - there are already 100 exams for this course (in case we need to add a new one)
 */

public class EditExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        EditExamRequest editExamRequest = (EditExamRequest) request;
        if (client.getInfo("userName") == null)
            return new EditExamResponse(UNAUTHORIZED, request);

        questionsAndExamsLock.lock();
        Exam exam = getTypeById(Exam.class, editExamRequest.getExamId(), session);

        if (exam == null)
        {
            questionsAndExamsLock.unlock();
            return new EditExamResponse(ERROR2, request);
        }

        if (exam.getAuthor() != getUser((String) client.getInfo("userName"), session))
        {
            questionsAndExamsLock.unlock();
            return new EditExamResponse(ERROR3, request);
        }

        List<Question> questionsList = new ArrayList<>();
        for (String question : editExamRequest.getQuestionsIDs())
            questionsList.add(getTypeById(Question.class, question, session));

        if (!exam.getConcreteExamsList().isEmpty())
        {
            if (exam.getCourse().getAvailableExamCodes().isEmpty())
            {
                questionsAndExamsLock.unlock();
                return new EditExamResponse(ERROR4, request);
            }

            Exam newExam = new Exam(exam.getCourse(), exam.getAuthor(), editExamRequest.getDurationInMinutes(),
                    editExamRequest.getExamTitle(), editExamRequest.getStudentNotes(), editExamRequest.getTeacherNotes(),
                    questionsList, editExamRequest.getScoresList());

            session.saveOrUpdate(newExam);
        }
        else
        {
            exam.setDurationInMinutes(editExamRequest.getDurationInMinutes());
            exam.setTitle(editExamRequest.getExamTitle());
            exam.setStudentNotes(editExamRequest.getStudentNotes());
            exam.setTeacherNotes(editExamRequest.getTeacherNotes());
            exam.setQuestionsScores(editExamRequest.getScoresList());
            exam.setQuestionsList(questionsList);

            session.saveOrUpdate(exam);

        }

        session.flush();

        questionsAndExamsLock.unlock();

        return new EditExamResponse(SUCCESS, request);
    }
}
