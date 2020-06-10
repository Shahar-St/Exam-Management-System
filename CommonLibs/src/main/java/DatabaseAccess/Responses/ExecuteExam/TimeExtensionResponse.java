package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * this is what the Teacher receives from the server after the Dean confirmed/rejected her time extension request.
 * <p>
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 * 3 - dean isn't logged in
 * 4 - error in communication with dean
 */

public class TimeExtensionResponse extends DatabaseResponse {

    private final String deanResponse;
    private final int authorizedTimeExtension;
    private final boolean isAccepted;

    public TimeExtensionResponse(int status, DatabaseRequest request, Boolean isAccepted, String deanResponse, int authorizedTimeExtension) {
        super(status, request);
        this.deanResponse = deanResponse;
        this.authorizedTimeExtension = authorizedTimeExtension;
        this.isAccepted = isAccepted;
    }
    public TimeExtensionResponse(int status, DatabaseRequest request) {
        this(status, request, false, null, 0);
    }

    public String getDeanResponse() {
        return deanResponse;
    }

    public int getAuthorizedTimeExtension() {
        return authorizedTimeExtension;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
