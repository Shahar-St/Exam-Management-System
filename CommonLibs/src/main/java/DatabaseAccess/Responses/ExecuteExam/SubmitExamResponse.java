package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class SubmitExamResponse extends DatabaseResponse {
    public SubmitExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
