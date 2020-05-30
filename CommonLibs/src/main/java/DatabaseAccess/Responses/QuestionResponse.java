package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightQuestion;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Request: asks for a question's full details
 * Response: the question's full details
 *
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - Question wasn't found
 */
public class QuestionResponse extends DatabaseResponse {

    private LightQuestion question;

    //successful request
    public QuestionResponse(int status, DatabaseRequest request, LightQuestion question) {
        super(status, request);
        this.question = question;
    }

    public LightQuestion getQuestion() {
        return question;
    }

    public void setQuestion(LightQuestion question) {
        this.question = question;
    }
}
