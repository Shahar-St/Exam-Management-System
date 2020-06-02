package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class AddExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        return null;
    }
}
