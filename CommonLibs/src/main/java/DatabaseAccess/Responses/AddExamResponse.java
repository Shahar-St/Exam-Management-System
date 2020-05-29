package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class AddExamResponse extends DatabaseResponse {
    public AddExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
