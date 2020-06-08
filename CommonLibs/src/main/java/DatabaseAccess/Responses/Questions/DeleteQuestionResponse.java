package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - Question wasn't found
 * 3 - user isn't the question's author
 * 4 - question is part of an exam
 */

public class DeleteQuestionResponse extends DatabaseResponse {

    public DeleteQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
