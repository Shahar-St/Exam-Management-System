package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExecutedExam;

public class GetExecutedExamResponse extends DatabaseResponse {

    private final LightExecutedExam lightExecutedExam;

    public GetExecutedExamResponse(int status, DatabaseRequest request, LightExecutedExam lightExecutedExam) {
        super(status, request);
        this.lightExecutedExam = lightExecutedExam;
    }

    public LightExecutedExam getExam() {
        return lightExecutedExam;
    }
}
