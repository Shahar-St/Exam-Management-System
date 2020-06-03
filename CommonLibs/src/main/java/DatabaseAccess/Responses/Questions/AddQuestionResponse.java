package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class AddQuestionResponse extends DatabaseResponse {
    public AddQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
