package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class SubmitExamResponse extends DatabaseResponse {
    public SubmitExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
