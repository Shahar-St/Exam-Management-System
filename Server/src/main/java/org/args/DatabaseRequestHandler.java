package org.args;

import DatabaseAccess.Requests.AllQuestionsRequest;
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
    private final ConnectionToClient client;

    public DatabaseRequestHandler(DatabaseRequest request, ConnectionToClient client, Session session) {
        this.request = request;
        this.session = session;
        this.client = client;

        if (request instanceof LoginRequest)
            loginHandler();
        else if (request instanceof AllQuestionsRequest)
            allQuestionHandler();
    }




    public DatabaseResponse getResponse() {
        return response;
    }

    private void loginHandler() {

    }

    private void allQuestionHandler() {
    }
}
