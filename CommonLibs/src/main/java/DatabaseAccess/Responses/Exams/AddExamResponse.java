package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class AddExamResponse extends DatabaseResponse {

    public AddExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }

}
