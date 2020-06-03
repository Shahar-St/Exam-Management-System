package DatabaseAccess.Requests.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightQuestion;

import java.util.List;

public class AddQuestionRequest extends DatabaseRequest {

    private final String newDescription;
    private final List<String> newAnswers;
    private final int correctAnswer;
    private final String courseID;

    public AddQuestionRequest(String newDescription, List<String> newAnswers, int correctAnswer, String courseID) {
        this.newDescription = newDescription;
        this.newAnswers = newAnswers;
        this.correctAnswer = correctAnswer;
        this.courseID = courseID;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public List<String> getNewAnswers() {
        return newAnswers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCourseID() {
        return courseID;
    }
}
