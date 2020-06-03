package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class DeleteQuestionResponse extends DatabaseResponse {

    public DeleteQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
