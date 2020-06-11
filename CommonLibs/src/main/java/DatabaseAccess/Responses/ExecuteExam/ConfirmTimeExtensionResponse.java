package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success (message sent successfully)
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class ConfirmTimeExtensionResponse extends DatabaseResponse {

    public ConfirmTimeExtensionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }

//    private final String examId;
//    private final int durationInMinutes;
//    private final String reasonForExtension;
//
//    public ConfirmTimeExtensionResponse(int status, DatabaseRequest request, String examId, int durationInMinutes, String reasonForExtension) {
//        super(status,request);
//        this.examId = examId;
//        this.durationInMinutes = durationInMinutes;
//        this.reasonForExtension = reasonForExtension;
//    }
//    public ConfirmTimeExtensionResponse(int unauthorized, DatabaseRequest request) {
//        super();
//    }
//
//    public String getExamId() {
//        return examId;
//    }
//
//    public int getDurationInMinutes() {
//        return durationInMinutes;
//    }
//
//    public String getReasonForExtension() {
//        return reasonForExtension;
//    }
}
