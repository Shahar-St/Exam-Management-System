package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class AddQuestionResponse extends DatabaseResponse {
    public AddQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
