package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

//
public class UncheckedExecutesOfConcreteRequest extends DatabaseRequest {
    private final String examId;

    public UncheckedExecutesOfConcreteRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }
}
