package DatabaseAccess.Requests;

import LightEntities.LightExecutedExam;

public class SubmitExamRequest extends DatabaseRequest {
    private LightExecutedExam lightExecutedExam;

    public SubmitExamRequest(LightExecutedExam lightExecutedExam) {
        this.lightExecutedExam = lightExecutedExam;
    }

    public LightExecutedExam getLightExecutedExam() {
        return lightExecutedExam;
    }

    public void setLightExecutedExam(LightExecutedExam lightExecutedExam) {
        this.lightExecutedExam = lightExecutedExam;
    }
}
