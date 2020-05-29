package LightEntities;

import java.time.LocalDateTime;
import java.util.List;

public class LightQuestion {
    private String questionContent;
    private List<String> answers;
    private int correctAnswer;
    private String author;
    private LocalDateTime lastModified;

    public LightQuestion() {
    }

    public LightQuestion(String questionContent, List<String> answers, int correctAnswer, String author, LocalDateTime lastModified) {
        this.questionContent = questionContent;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.author = author;
        this.lastModified = lastModified;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
