package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * this is sent to the Dean once a teacher sends a time extension request.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 * 3 - dean isn't logged in
 * 4 - error in communication with dean
 */

public class ConfirmTimeExtensionResponse extends DatabaseResponse {

    private final String examId;
    private final int durationInMinutes;
    private final String reasonForExtension;

    public ConfirmTimeExtensionResponse(int status, DatabaseRequest request, String examId, int durationInMinutes, String reasonForExtension) {
        super(status, request);
        this.examId = examId;
        this.durationInMinutes = durationInMinutes;
        this.reasonForExtension = reasonForExtension;
    }
    public ConfirmTimeExtensionResponse(int status, DatabaseRequest request) {
        this(status, request, null, 0, null);
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
