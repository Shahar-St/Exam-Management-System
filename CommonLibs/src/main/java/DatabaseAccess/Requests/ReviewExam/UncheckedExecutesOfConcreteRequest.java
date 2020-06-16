package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * sending a specific exam in order to receive all student exams that were still not evaluated.
 */
public class UncheckedExecutesOfConcreteRequest extends DatabaseRequest {
    private final String concreteExamId;

    public UncheckedExecutesOfConcreteRequest(String examId) {
        this.concreteExamId = examId;
    }

    public String getExamId() {
        return concreteExamId;
    }
}
