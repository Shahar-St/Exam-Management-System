package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.time.LocalDateTime;

/**
 * Request: asks for a question's full details
 * Response: a string contains the question's full details
 */
public class QuestionResponse extends DatabaseResponse{

    private final String questionContent;
    private String[] answers;
    private int correctAnswer;
    private String courseName;
    private String author;
    private LocalDateTime lastModified;

    public QuestionResponse(boolean status, DatabaseRequest request,  ) {
        super(status, request);
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return questionContent;
    }
}
