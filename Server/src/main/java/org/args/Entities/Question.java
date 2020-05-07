package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    private static DecimalFormat decimalFormat = new DecimalFormat("000");

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String questionContent;
    private String[] answersArray;  // 4 answers
    private int correctAnswer; // the index-1 of the correct answer in the answers array

    // not sure it's needed
    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinTable(
            name = "questions_exams",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<Exam> containedInExams = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    private LocalDateTime lastModified;

    //Group c'tors
    public Question() { }

    public Question(String questionContent, String[] answersArray, int correctAnswer, Course course, Teacher author) {
        this.questionContent = questionContent;
        this.answersArray = answersArray;
        this.correctAnswer = correctAnswer;
        this.course = course;
        author.addQuestion(this);
        //this.author = author;
        updateLastModified();

        // handle empty queue
        this.id = course.getId() + decimalFormat.format(course.getAvailableQuestionCodes().poll());
    }

    //Group adders and removers
    public void addExam(Exam exam) {
        if (!containedInExams.contains(exam))
            containedInExams.add(exam);

        if (!exam.getQuestionsList().contains(this))
            exam.getQuestionsList().add(this);
    }

    //Group setters and getters
    public String getId() {
        return id;
    }
    protected void setId(String id) { this.id = id; }

    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String[] getAnswersArray() {
        return answersArray;
    }
    public void setAnswersArray(String[] answersArray) {
        this.answersArray = answersArray;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Exam> getContainedInExams() {
        return containedInExams;
    }
    public void setContainedInExams(List<Exam> containedInExams) {
        this.containedInExams = containedInExams;
    }

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getAuthor() {
        return author;
    }
    public void setAuthor(Teacher author) {
        this.author = author;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    //Group other methods

    // update last modified field using current time
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }

}
