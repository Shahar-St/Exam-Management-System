package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * notifies the teacher that her time extension request was delivered successfully.
 * status dictionary:
 * 0 - success (message sent successfully)
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TimeExtensionResponse extends DatabaseResponse {

    public TimeExtensionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }

}
