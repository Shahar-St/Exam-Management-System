package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class ExecuteExamRequest extends DatabaseRequest {
    private LightExam lightExam;
    private String examCode;

    public ExecuteExamRequest(LightExam lightExam, String examCode) {
        this.lightExam = lightExam;
        this.examCode = examCode;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }
}
