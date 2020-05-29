package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class DeleteExamResponse extends DatabaseResponse {
    public DeleteExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
