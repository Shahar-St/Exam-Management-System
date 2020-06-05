package org.args.DatabaseStrategies;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.LoginResponse;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class LoginStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        LoginRequest loginRequest = (LoginRequest) request;

        User user = getUser(loginRequest.getUserName(), session);

        if (user == null || !user.getUserName().equals(loginRequest.getUserName()))
            return new LoginResponse(ERROR2, request);

        if (loggedInUsers.contains(loginRequest.getUserName()))
            return new LoginResponse(ERROR3, request);

        if (!user.getPassword().equals(loginRequest.getPassword()))
            return new LoginResponse(ERROR4, request);

        client.setInfo("userName", user.getUserName());
        loggedInUsers.add(user.getUserName());
        return new LoginResponse(SUCCESS, user.getClass().getSimpleName().toLowerCase(),
                user.getFullName(), request);

    }
}
