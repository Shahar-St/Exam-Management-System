package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExam;

public class DeleteExamRequest  extends DatabaseRequest {
    private final String examId;

    public DeleteExamRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

}
