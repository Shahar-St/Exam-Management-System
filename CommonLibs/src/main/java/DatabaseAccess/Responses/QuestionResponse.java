package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.time.LocalDateTime;

/**
 * Request: asks for a question's full details
 * Response: a string contains the question's full details
 */
public class QuestionResponse extends DatabaseResponse{

    private final String questionContent;
    private final String[] answers;
    private final int correctAnswer;
    private final String courseName;
    private final String author;
    private final LocalDateTime lastModified;

    public QuestionResponse(boolean status, DatabaseRequest request, String questionContent, String[] answers, int correctAnswer, String courseName, String author, LocalDateTime lastModified  ) {
        super(status, request);
        this.questionContent = questionContent;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.courseName = courseName;
        this.author = author;
        this.lastModified = lastModified;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

}
