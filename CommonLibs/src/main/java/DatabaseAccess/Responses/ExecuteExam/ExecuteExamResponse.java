package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class ExecuteExamResponse extends DatabaseResponse {

    private final String examID;

    public ExecuteExamResponse(int status, DatabaseRequest request, String examID) {
        super(status, request);
        this.examID = examID;
    }

    public ExecuteExamResponse(int status, DatabaseRequest request) {
        this(status, request, null);
    }

    public String getExamID() {
        return examID;
    }
}
