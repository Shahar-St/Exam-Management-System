package DatabaseAccess.Requests;

/**
 * Request: asks to edit a question
 * Response: just status
 */
public class EditQuestionRequest extends DatabaseRequest {

    private final int questionID;
    private final String newDescription;
    private final String[] newAnswers;
    private final int correctAnswer;

    public EditQuestionRequest(int questionID, String newDescription, String[] newAnswers, int correctAnswer) {
        this.questionID = questionID;
        this.newDescription = newDescription;
        this.newAnswers = newAnswers;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionID() {
        return questionID;
    }
    public String getNewDescription() {
        return newDescription;
    }
    public String[] getNewAnswers() {
        return newAnswers;
    }
    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
