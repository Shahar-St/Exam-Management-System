package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * Request: asks to edit a question
 * Response: just status
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - exam wasn't found
 *  3 - trying to delete an exam that wasn't written by the user
 */
public class EditExamResponse extends DatabaseResponse {
    public EditExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
