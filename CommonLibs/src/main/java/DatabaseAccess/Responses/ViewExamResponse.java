package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExam;

public class ViewExamResponse extends DatabaseResponse {
    private LightExam lightExam;

    public ViewExamResponse(int status, DatabaseRequest request,LightExam exam) {
        super(status, request);
        lightExam = exam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }
}
