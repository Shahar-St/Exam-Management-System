package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success (message sent successfully)
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TimeExtensionResponse extends DatabaseResponse {

    public TimeExtensionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }

//    private final String deanResponse;
//    private final int authorizedTimeExtension;
//    private final boolean isAccepted;
//
//    public TimeExtensionResponse(int status, DatabaseRequest request, Boolean isAccepted, String deanResponse, int authorizedTimeExtension) {
//        super(status, request);
//        this.deanResponse = deanResponse;
//        this.authorizedTimeExtension = authorizedTimeExtension;
//        this.isAccepted = isAccepted;
//    }
//    public TimeExtensionResponse(int status, DatabaseRequest request) {
//        this(status, request, false, null, 0);
//    }
//
//    public String getDeanResponse() {
//        return deanResponse;
//    }
//
//    public int getAuthorizedTimeExtension() {
//        return authorizedTimeExtension;
//    }
//
//    public boolean isAccepted() {
//        return isAccepted;
//    }
}
