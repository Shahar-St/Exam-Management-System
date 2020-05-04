package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String questionContent;

    private String questionDescription;

    private String [] answersArray;
    // the index of the correct answer in the answers array
    private int correctAnswer;

    private Subject questionSubject;

    private Course questionCourse;

    private Teacher author;

    private String lastModified;

    public Question() {
    }

    public Question(String questionContent, String questionDescription, String[] answersArray, int correctAnswer, Subject questionSubject, Course questionCourse, Teacher author) {
        this.questionContent = questionContent;
        this.questionDescription = questionDescription;
        this.answersArray = answersArray;
        this.correctAnswer = correctAnswer;
        this.questionSubject = questionSubject;
        this.questionCourse = questionCourse;
        this.author = author;
        this.updateLastModified();
    }

    public String getQuestionContent() {
        return questionContent;
    }

    protected void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    protected void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String[] getAnswersArray() {
        return answersArray;
    }

    protected void setAnswersArray(String[] answersArray) {
        this.answersArray = answersArray;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    protected void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Subject getQuestionSubject() {
        return questionSubject;
    }

    protected void setQuestionSubject(Subject questionSubject) {
        this.questionSubject = questionSubject;
    }

    public Course getQuestionCourse() {
        return questionCourse;
    }

    protected void setQuestionCourse(Course questionCourse) {
        this.questionCourse = questionCourse;
    }

    public Teacher getAuthor() {
        return author;
    }

    protected void setAuthor(Teacher author) {
        this.author = author;
    }

    public String getLastModified() {
        return lastModified;
    }

    protected void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    // update last modified field using current time
    private void updateLastModified(){
        this.lastModified = LocalDateTime.now().toString();
    }
}
