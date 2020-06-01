package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExam;

public class TimeExtensionRequest extends DatabaseRequest {
    private final String examId;
    private final int durationInMinutes;
    private final String reasonForExtension;

    public TimeExtensionRequest(String examId, int durationInMinutes, String reasonForExtension) {
        this.examId = examId;
        this.durationInMinutes = durationInMinutes;
        this.reasonForExtension = reasonForExtension;
    }

    public String getExamId() {
        return examId;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getReasonForExtension() {
        return reasonForExtension;
    }
}
