package DatabaseAccess.Requests;

/**
 * Request: asks for a question's full details
 * Response: a string contains the question's full details
 */
public class QuestionRequest extends DatabaseRequest{

    private final int questionID;

    public QuestionRequest(int questionID) {
        this.questionID = questionID;
    }

    public int getQuestionID() {
        return questionID;
    }
}
