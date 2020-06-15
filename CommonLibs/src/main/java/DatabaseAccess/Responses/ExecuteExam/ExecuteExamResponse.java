package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * initiates the exam requested by the teacher.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class ExecuteExamResponse extends DatabaseResponse {

    private final String concreteExamID;
    private final int duration;

    public ExecuteExamResponse(int status, DatabaseRequest request, String concreteExamID,int duration) {
        super(status, request);
        this.concreteExamID = concreteExamID;
        this.duration = duration;
    }

    public ExecuteExamResponse(int status, DatabaseRequest request) {
        this(status, request, null,0);
    }

    public String getConcreteExamID() {
        return concreteExamID;
    }

    public int getDuration() {
        return duration;
    }
}
