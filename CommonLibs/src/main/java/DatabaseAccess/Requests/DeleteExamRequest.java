package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class DeleteExamRequest  extends DatabaseRequest {
    private LightExam lightExam;

    public DeleteExamRequest(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }
}
