package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to edit a question
 * Response: just status
 */
public class EditQuestionResponse extends DatabaseResponse {

    public EditQuestionResponse(boolean status, DatabaseRequest request, String errorMsg) {
        super(status, request, errorMsg);
    }
}
