package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.ViewExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */
public class ViewExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        ViewExamRequest viewExamRequest = (ViewExamRequest) request;

        if (client.getInfo("userName") == null)
            return new ViewExamResponse(UNAUTHORIZED, viewExamRequest);

        questionsAndExamsLock.lock();

        Exam exam = getTypeById(Exam.class, viewExamRequest.getExamId(), session);

        if (exam == null)
        {
            questionsAndExamsLock.unlock();
            return new ViewExamResponse(ERROR2, viewExamRequest);
        }

        questionsAndExamsLock.unlock();
        return new ViewExamResponse(SUCCESS, request, exam.createLightExam());
    }
}
