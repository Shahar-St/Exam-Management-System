package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class EvaluateManualExamResponse extends DatabaseResponse {
    public EvaluateManualExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
