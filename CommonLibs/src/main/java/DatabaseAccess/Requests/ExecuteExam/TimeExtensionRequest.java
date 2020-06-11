package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

//this is what the teacher sends to the server when the teacher requests time extension.

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
