package DatabaseAccess.Requests;

import LightEntities.LightExam;

public class TimeExtensionRequest extends DatabaseRequest {
    private LightExam lightExam;
    private int durationInMinutes;
    private String reasonForExtension;

    public TimeExtensionRequest(LightExam lightExam, int durationInMinutes, String reasonForExtension) {
        this.lightExam = lightExam;
        this.durationInMinutes = durationInMinutes;
        this.reasonForExtension = reasonForExtension;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getReasonForExtension() {
        return reasonForExtension;
    }

    public void setReasonForExtension(String reasonForExtension) {
        this.reasonForExtension = reasonForExtension;
    }
}
