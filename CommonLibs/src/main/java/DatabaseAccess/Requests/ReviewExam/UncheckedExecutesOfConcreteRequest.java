package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

//
public class UncheckedExecutesOfConcreteRequest extends DatabaseRequest {
    private final String concreteExamId;

    public UncheckedExecutesOfConcreteRequest(String examId) {
        this.concreteExamId = examId;
    }

    public String getExamId() {
        return concreteExamId;
    }
}
