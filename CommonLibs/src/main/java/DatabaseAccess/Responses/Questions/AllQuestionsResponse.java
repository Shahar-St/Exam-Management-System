package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * returns all information needed to present a list of questions from requested course.
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 */
public class AllQuestionsResponse extends DatabaseResponse {

    private final HashMap<String, Pair<LocalDateTime, String>> questionList;

    public AllQuestionsResponse(int status, DatabaseRequest request,
                                HashMap<String, Pair<LocalDateTime, String>> questions) {
        super(status, request);
        questionList = questions;

    }

    public AllQuestionsResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.questionList = null;
    }

    public HashMap<String, Pair<LocalDateTime, String>> getQuestionList() {
        return questionList;
    }
}
