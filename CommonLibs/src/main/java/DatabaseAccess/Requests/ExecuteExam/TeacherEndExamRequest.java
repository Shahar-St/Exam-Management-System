package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherEndExamRequest extends DatabaseRequest {
    private final String concreteExamId;

    public TeacherEndExamRequest(String concreteExamId) {
        this.concreteExamId = concreteExamId;
    }

    public String getConcreteExamId() {
        return concreteExamId;
    }
}
