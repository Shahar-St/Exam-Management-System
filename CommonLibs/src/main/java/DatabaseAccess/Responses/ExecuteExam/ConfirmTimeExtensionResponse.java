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

}
