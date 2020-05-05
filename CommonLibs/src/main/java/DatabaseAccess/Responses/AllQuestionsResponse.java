package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.Date;
import java.util.HashMap;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllQuestionsResponse extends DatabaseResponse {

    private final HashMap<Integer, Pair<Date, String>> questions;

    public AllQuestionsResponse(boolean status, DatabaseRequest request,
                                HashMap<Integer, Pair<Date, String>> questions) {
        super(status, request);
        this.questions = questions;
    }

    public HashMap<Integer, Pair<Date, String>> getQuestions() {
        return questions;
    }
}
