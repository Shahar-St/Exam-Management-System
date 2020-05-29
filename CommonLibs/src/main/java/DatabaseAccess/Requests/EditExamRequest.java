package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class EditExamRequest extends DatabaseRequest {
    private LightExam lightExam;

    public EditExamRequest(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }
}
