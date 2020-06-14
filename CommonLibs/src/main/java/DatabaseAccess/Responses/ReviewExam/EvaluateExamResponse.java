package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class EvaluateExamResponse extends DatabaseResponse {

    public EvaluateExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
