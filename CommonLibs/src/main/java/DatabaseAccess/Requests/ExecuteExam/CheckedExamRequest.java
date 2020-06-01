package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

public class CheckedExamRequest extends DatabaseRequest {
    private final String examId;

    public CheckedExamRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }
}
