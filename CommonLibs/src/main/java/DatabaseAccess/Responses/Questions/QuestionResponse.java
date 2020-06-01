package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
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

    private final String questionContent;
    private final List<String> answers;
    private final int correctAnswer;
    private final String author;
    private final LocalDateTime lastModified;

    public QuestionResponse(int status, DatabaseRequest request, String questionContent, List<String> answers, int correctAnswer, String author, LocalDateTime lastModified) {
        super(status, request);
        this.questionContent = questionContent;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.author = author;
        this.lastModified = lastModified;
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

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
}
