package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class AddQuestionResponse extends DatabaseResponse {
    public AddQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
