package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class SubmitExamResponse extends DatabaseResponse {
    public SubmitExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
