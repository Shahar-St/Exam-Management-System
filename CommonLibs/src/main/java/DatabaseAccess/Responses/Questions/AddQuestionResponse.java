package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - there are already 1000 question for this course
 */

public class AddQuestionResponse extends DatabaseResponse {
    public AddQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
