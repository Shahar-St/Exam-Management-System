package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.time.LocalDateTime;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllQuestionsResponse extends DatabaseResponse {

    private final LocalDateTime questions;

    public AllQuestionsResponse(boolean status, DatabaseRequest request,
                                LocalDateTime questions) {
        super(status, request);
        this.questions = questions;
    }

    public LocalDateTime getQuestions() {
        return questions;
    }
}
