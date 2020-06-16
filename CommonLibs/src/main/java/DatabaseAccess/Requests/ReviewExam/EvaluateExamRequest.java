package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExecutedExam;

/**
 * when a teacher wants to evaluate a specific student's exam.
 */
public class EvaluateExamRequest extends DatabaseRequest {

    private final LightExecutedExam exam;

    public EvaluateExamRequest(LightExecutedExam exam) {
        this.exam = exam;
    }

    public LightExecutedExam getExam() {
        return exam;
    }
}
