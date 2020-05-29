package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class TakeExamResponse extends DatabaseResponse {
    public TakeExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
