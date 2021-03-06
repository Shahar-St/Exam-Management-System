package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.QuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Request: asks for a question's full details
 * Response: the question's full details
 * <p>
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - Question wasn't found
 */
public class QuestionResponse extends DatabaseResponse {

    private final String questionContent;
    private final List<String> answers;
    private final int correctAnswer;
    private final String authorUserName;
    private final LocalDateTime lastModified;

    // successful request
    public QuestionResponse(int status, DatabaseRequest request, String questionContent, List<String> answers,
                            int correctAnswer, String authorUserName, LocalDateTime lastModified) {
        super(status, request);
        this.questionContent = questionContent;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.authorUserName = authorUserName;
        this.lastModified = lastModified;
    }

    // unsuccessful request
    public QuestionResponse(int failedCode, QuestionRequest request) {
        super(failedCode, request);
        questionContent = null;
        answers = null;
        correctAnswer = -1;
        authorUserName = null;
        lastModified = null;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
}
