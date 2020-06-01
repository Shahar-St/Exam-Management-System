package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class DeleteQuestionResponse extends DatabaseResponse {
    public DeleteQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
