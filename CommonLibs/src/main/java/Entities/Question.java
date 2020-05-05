package Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String questionContent;

    private String[] answersArray;
    // the index of the correct answer in the answers array
    private int correctAnswer;

    @ManyToMany
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "questions_exams",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<Exam> containingExams;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course questionCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    private String lastModified;

    private String questionCourseId;

    public Question() { this.containingExams = new ArrayList<>(); }

    public Question(String questionContent, String questionDescription, String[] answersArray, int correctAnswer, Course questionCourse, Teacher author) {
        this.questionContent = questionContent;
        this.answersArray = answersArray;
        this.correctAnswer = correctAnswer;
        this.questionCourse = questionCourse;
        this.author = author;
        this.containingExams = new ArrayList<>();
        this.updateLastModified();
        // unique question identifier not fot db , for question serial encoding
        this.questionCourseId = String.valueOf(this.questionCourse.getCourseQuestionCounter());
        this.questionCourse.updateCourseQuestionCounter(); // increment the subject questions counter
    }

    public String getQuestionContent() {
        return questionContent;
    }

    protected void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
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
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now().toString();
    }

    public String getQuestionCourseId() {
        return questionCourseId;
    }

    protected void setQuestionCourseId(String questionCourseId) {
        this.questionCourseId = questionCourseId;
    }

    public String getQuestionId() {
        return this.questionCourse.getCourseId() + this.questionCourseId;
    }

    public void addCourseExamList(Exam exam, double score) {
        this.containingExams.add(exam);
        exam.addExamQuestion(this, score);
    }
}
