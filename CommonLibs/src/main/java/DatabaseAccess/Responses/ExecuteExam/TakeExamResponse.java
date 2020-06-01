package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

public class TakeExamResponse extends DatabaseResponse {
    private final LightExam lightExam;

    public TakeExamResponse(int status, DatabaseRequest request, LightExam lightExam) {
        super(status, request);
        this.lightExam = lightExam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }
}
