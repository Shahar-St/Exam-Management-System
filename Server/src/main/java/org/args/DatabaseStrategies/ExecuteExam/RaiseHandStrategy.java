package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.RaiseHandResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

//TODO
public class RaiseHandStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        return null;
    }
}
