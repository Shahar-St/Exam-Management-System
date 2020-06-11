package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.TimeExtensionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.TimeExtensionResponse;
import Notifiers.TimeExtensionRequestNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.Exam;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TimeExtensionStrategy extends DatabaseStrategy implements IExamInProgress {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        TimeExtensionRequest request1 = (TimeExtensionRequest) request;

        if (client.getInfo("userName") == null)
            return new TimeExtensionResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        if (concreteExam == null)
            return new TimeExtensionResponse(ERROR2, request);

        return new TimeExtensionResponse(SUCCESS, request);
    }

    @Override
    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {

        TimeExtensionRequest request1 = (TimeExtensionRequest) request;
        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamId(), session);
        Exam exam = concreteExam.getExam();

        TimeExtensionRequestNotifier notifier = new TimeExtensionRequestNotifier(exam.getCourse().getSubject().getName(),
                exam.getCourse().getName(), concreteExam.getTester().getFullName(), exam.getTitle(),
                String.valueOf(concreteExam.getId()), request1.getDurationInMinutes(), request1.getReasonForExtension());

        ExamManager.askForTimeExtension(notifier);
    }
}
