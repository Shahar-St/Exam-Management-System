package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks for a question's full details
 * Response: a string contains the question's full details
 */
public class QuestionResponse extends DatabaseResponse{

    private final String questionDetails;

    public QuestionResponse(boolean status, DatabaseRequest request, String questionDetails) {
        super(status, request);
        this.questionDetails = questionDetails;
    }

    public String getQuestionDetails() {
        return questionDetails;
    }
}
