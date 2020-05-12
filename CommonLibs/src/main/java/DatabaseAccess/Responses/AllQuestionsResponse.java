package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 *
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 */
public class AllQuestionsResponse extends DatabaseResponse {

    private final HashMap<Integer, Pair<LocalDateTime, String>> questionList;

    public AllQuestionsResponse(int status, DatabaseRequest request,
                                HashMap<Integer, Pair<LocalDateTime, String>> questions) {
        super(status, request);
        questionList = questions;

    }

    public AllQuestionsResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.questionList = null;
    }
    public HashMap<Integer, Pair<LocalDateTime, String>> getQuestionList() {
        return questionList;
    }
}

