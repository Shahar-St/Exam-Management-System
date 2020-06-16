package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * a student receives this in order to notify him his request was delivered successfully.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class RaiseHandResponse extends DatabaseResponse {

    public RaiseHandResponse(int status, DatabaseRequest request)
    {
        super(status, request);
    }

}
