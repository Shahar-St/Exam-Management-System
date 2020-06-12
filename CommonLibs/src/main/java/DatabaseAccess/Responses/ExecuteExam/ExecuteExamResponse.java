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

    private final String concreteExamID;

    public ExecuteExamResponse(int status, DatabaseRequest request, String concreteExamID) {
        super(status, request);
        this.concreteExamID = concreteExamID;
    }

    public ExecuteExamResponse(int status, DatabaseRequest request) {
        this(status, request, null);
    }

    public String getConcreteExamID() {
        return concreteExamID;
    }
}
