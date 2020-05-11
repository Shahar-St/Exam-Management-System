package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to edit a question
 * Response: just status
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - Question wasn't found
 *  3 - trying to edit a question that wasn't written by the user
 */
public class EditQuestionResponse extends DatabaseResponse {

    public EditQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}