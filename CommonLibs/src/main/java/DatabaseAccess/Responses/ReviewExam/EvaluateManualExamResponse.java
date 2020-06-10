package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class EvaluateManualExamResponse extends DatabaseResponse {
    public EvaluateManualExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
