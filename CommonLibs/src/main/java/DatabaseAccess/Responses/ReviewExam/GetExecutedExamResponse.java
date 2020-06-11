package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExecutedExam;

public class GetExecutedExamResponse extends DatabaseResponse {

    private final LightExecutedExam exam;

    public GetExecutedExamResponse(int status, DatabaseRequest request, LightExecutedExam exam) {
        super(status, request);
        this.exam = exam;
    }

    public LightExecutedExam getExam() {
        return exam;
    }
}
