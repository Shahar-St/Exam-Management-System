package DatabaseAccess.Requests.Questions;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks for a question's full details
 * Response: the question's full details
 */
public class QuestionRequest extends DatabaseRequest{

    private final String questionID;

    public QuestionRequest(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionID() {
        return questionID;
    }
}