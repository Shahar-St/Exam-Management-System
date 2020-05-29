package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class DeleteQuestionResponse extends DatabaseResponse {
    public DeleteQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
