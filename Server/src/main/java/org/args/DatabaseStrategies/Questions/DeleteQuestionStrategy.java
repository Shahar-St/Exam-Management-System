package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class DeleteQuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        return null;
    }
}
