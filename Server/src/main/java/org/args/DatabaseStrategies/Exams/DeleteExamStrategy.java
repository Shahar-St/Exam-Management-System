package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.DeleteExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.DeleteExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class DeleteExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        DeleteExamRequest deleteExamRequest = (DeleteExamRequest) request;
        if (client.getInfo("userName") == null)
            return new DeleteExamResponse(UNAUTHORIZED, request);

        questionsAndExamsLock.lock();
        Exam exam = getTypeById(Exam.class, deleteExamRequest.getExamId(), session);

        if (exam == null)
        {
            questionsAndExamsLock.unlock();
            return new DeleteExamResponse(ERROR2, request);
        }

        if (exam.getAuthor() != getUser((String) client.getInfo("userName"), session))
        {
            questionsAndExamsLock.unlock();
            return new DeleteExamResponse(ERROR3, request);
        }

        if (!exam.getConcreteExamsList().isEmpty())
        {
            questionsAndExamsLock.unlock();
            return new DeleteExamResponse(ERROR4, request);
        }

        exam.getCourse().getAvailableExamCodes().add(Integer.parseInt(exam.getId().substring(4)));

        session.remove(exam);
        session.flush();

        questionsAndExamsLock.unlock();
        return new DeleteExamResponse(SUCCESS, request);
    }
}
