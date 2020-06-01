package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;

public class ViewExamRequest extends DatabaseRequest {
    private final String examId;

    public ViewExamRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

}
