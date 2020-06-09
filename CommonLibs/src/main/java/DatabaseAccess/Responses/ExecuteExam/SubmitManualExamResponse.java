package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class SubmitManualExamResponse extends DatabaseResponse {
    public SubmitManualExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
