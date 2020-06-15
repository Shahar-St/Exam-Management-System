package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * teacher sends this when the time expires.
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
