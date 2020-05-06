package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class DatabaseRequestHandler {

    private DatabaseResponse response;

    public DatabaseRequestHandler(DatabaseRequest request) {

    }

    public DatabaseResponse getResponse() {
        return response;
    }
}
