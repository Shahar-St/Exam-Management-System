package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class ConfirmAutomatedEvaluationResponse extends DatabaseResponse {
    public ConfirmAutomatedEvaluationResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
