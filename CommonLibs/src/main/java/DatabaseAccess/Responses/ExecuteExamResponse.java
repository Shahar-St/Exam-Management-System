package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class ExecuteExamResponse extends DatabaseResponse {
    public ExecuteExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
