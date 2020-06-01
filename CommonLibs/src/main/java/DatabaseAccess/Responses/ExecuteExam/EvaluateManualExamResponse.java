package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class EvaluateManualExamResponse extends DatabaseResponse {
    public EvaluateManualExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
