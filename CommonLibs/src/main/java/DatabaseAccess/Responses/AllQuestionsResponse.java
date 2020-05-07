package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllQuestionsResponse extends DatabaseResponse {

    private final HashMap<Integer, Pair<LocalDateTime, String>> questionList;

    public AllQuestionsResponse(boolean status, DatabaseRequest request,
                                HashMap<Integer, Pair<LocalDateTime, String>> questions, String errorMsg) {
        super(status, request, errorMsg);
        questionList = questions;
    }

    public HashMap<Integer, Pair<LocalDateTime, String>> getQuestionList() {
        return questionList;
    }
}
