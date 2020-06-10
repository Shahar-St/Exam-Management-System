package org.args.DatabaseStrategies;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.LoginResponse;
import org.args.Entities.Dean;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - username wasn't found
 * 3 - user is currently logged in from a different terminal"
 * 4 - wrong password
 */

public class LoginStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   Map<String, ConnectionToClient> loggedInUsers) {

        LoginRequest loginRequest = (LoginRequest) request;

        User user = getUser(loginRequest.getUserName(), session);

        if (user == null || !user.getUserName().equals(loginRequest.getUserName()))
            return new LoginResponse(ERROR2, request);

        if (loggedInUsers.containsKey(loginRequest.getUserName()))
            return new LoginResponse(ERROR3, request);

        if (!user.getPassword().equals(loginRequest.getPassword()))
            return new LoginResponse(ERROR4, request);

        client.setInfo("userName", user.getUserName());
        loggedInUsers.put(user.getUserName(), client);
        if (user instanceof Dean)
        {
            loggedInUsers.put("DeanConnection", client);
            client.setInfo("Dean", true);
        }
        return new LoginResponse(SUCCESS, user.getClass().getSimpleName().toLowerCase(),
                user.getFullName(), request);

    }
}
