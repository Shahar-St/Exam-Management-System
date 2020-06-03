package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.DeleteExamRequest;
import DatabaseAccess.Requests.Questions.DeleteQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.DeleteExamResponse;
import DatabaseAccess.Responses.Questions.DeleteQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class DeleteExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        DeleteExamRequest deleteExamRequest = (DeleteExamRequest) request;
        if (client.getInfo("userName") == null)
            return new DeleteQuestionResponse(UNAUTHORIZED, request);

        Exam exam = getTypeById(Exam.class, deleteExamRequest.getExamId(), session);

        if (exam == null)
            return new DeleteExamResponse(NOT_FOUND, request);

        if (exam.getAuthor() != getUser((String) client.getInfo("userName"), session))
            return new DeleteQuestionResponse(NO_ACCESS, request);

        if (!exam.getExecutedExamsList().isEmpty())
            return new DeleteExamResponse(WRONG_INFO, request);

        session.remove(exam);
        session.flush();
        return new DeleteExamResponse(SUCCESS, request);

    }
}
