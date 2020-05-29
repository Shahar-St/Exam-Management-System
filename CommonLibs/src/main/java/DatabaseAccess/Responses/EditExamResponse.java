package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class EditExamResponse extends DatabaseResponse {
    public EditExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
