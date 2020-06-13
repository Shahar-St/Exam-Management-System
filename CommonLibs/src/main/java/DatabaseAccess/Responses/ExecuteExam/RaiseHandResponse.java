package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class RaiseHandResponse extends DatabaseResponse {
    public RaiseHandResponse(int status, DatabaseRequest request, String studentName)
    {
        super(status, request);
    }

}
