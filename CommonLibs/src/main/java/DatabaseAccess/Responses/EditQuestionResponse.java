package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to edit a question
 * Response: just status
 */
public class EditQuestionResponse extends DatabaseResponse {

    // successful request
    public EditQuestionResponse(boolean status, DatabaseRequest request) {
        super(status, request, null);
    }

    // unsuccessful request
    public EditQuestionResponse(boolean status, DatabaseRequest request, String errorMsg) {
        super(status, request, errorMsg);
    }
}
