package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.LoginResponse;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class DatabaseRequestHandler {

    private final DatabaseRequest request;
    private DatabaseResponse response;
    private final Session session;

    public DatabaseRequestHandler(DatabaseRequest request,
                               //   ConnectionToClient client,
                                  Session session) {
        this.request = request;
        this.session = session;

        if (request instanceof LoginRequest)
            loginHandler();



    }

    public DatabaseResponse getResponse() {
        return response;
    }

    private void loginHandler() {

        boolean status = true;
        String permission = "";
        String errorMsg = "";
        try
        {
            LoginRequest loginRequest = (LoginRequest) request;
            String hql = "FROM User U WHERE U.userName = " + loginRequest.getUserName();
            Query query = session.createQuery(hql);
            List<User> results = query.getResultList();
            if (results.size() == 0)
            {
                status = false;
                errorMsg = "User name was not found";
            }
            else if (!results.get(0).getPassword().equals(loginRequest.getPassword()))
            {
                status = false;
                errorMsg = "Incorrect password";
            }
            else
                permission = results.get(0).getClass().getName();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            status = false;
        }
        response = new LoginResponse(status, request, permission, errorMsg);
    }
}
