package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TeacherEndExamRequest extends DatabaseRequest {

    private final String concreteExamId;

    public TeacherEndExamRequest(String concreteExamId) {
        this.concreteExamId = concreteExamId;
    }

    public String getConcreteExamId() {
        return concreteExamId;
    }
}
