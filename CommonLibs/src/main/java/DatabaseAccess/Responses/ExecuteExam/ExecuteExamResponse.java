package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class ExecuteExamResponse extends DatabaseResponse {
    public ExecuteExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
