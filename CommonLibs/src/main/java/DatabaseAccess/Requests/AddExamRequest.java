package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class AddExamRequest extends DatabaseRequest {
    private LightExam lightExam;

    public AddExamRequest(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }
}
