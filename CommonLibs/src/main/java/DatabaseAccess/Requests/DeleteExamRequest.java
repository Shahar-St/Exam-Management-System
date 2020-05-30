package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class DeleteExamRequest  extends DatabaseRequest {
    private String examId;

    public DeleteExamRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }
}
