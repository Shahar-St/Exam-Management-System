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

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String questionContent;
    private int correctAnswer; // the index-1 of the correct answer in the answersArray

    @ElementCollection
    private List<String> answersArray = new ArrayList<>();    // 4 answers

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinTable(
            name = "questions_exams",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<Exam> containedInExams = new ArrayList<>();

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinTable(
            name = "questions_executedExams",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "executedExam_id")
    )
    private final List<ExecutedExam> containedInExecutedExams = new ArrayList<>();

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

    public Question(String questionContent, List<String> answersArray, int correctAnswer, Course course, Teacher author) {
        this.questionContent = questionContent;
        this.answersArray = answersArray;
        this.correctAnswer = correctAnswer;
        course.addQuestion(this);
        author.addQuestion(this);
        setLastModified();

        // handle empty queue
        DecimalFormat decimalFormat = new DecimalFormat("000");
        this.id = course.getId() + decimalFormat.format(course.getAvailableQuestionCodes().poll());
    }

    //Group adders and removers
    public void addExam(Exam exam) {
        if (!containedInExams.contains(exam))
            containedInExams.add(exam);

        if (!exam.getQuestionsList().contains(this))
            exam.getQuestionsList().add(this);
    }

    public void addExecutedExam(ExecutedExam executedExam) {
        if (!containedInExecutedExams.contains(executedExam))
            containedInExecutedExams.add(executedExam);

        if (!executedExam.getQuestionsList().contains(this))
            executedExam.getQuestionsList().add(this);
    }

    //Group setters and getters
    public String getId() {
        return id;
    }

    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<String> getAnswersArray() {
        return answersArray;
    }
    public void setAnswersArray(List<String> answersArray) {
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
        if(course.getQuestionsList().contains(this))
            course.addQuestion(this);
    }

    public Teacher getAuthor() {
        return author;
    }
    public void setAuthor(Teacher author) {

        this.author = author;
        if(!author.getQuestionsList().contains(this))
            author.addQuestion(this);
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
    public void setLastModified() {
        this.lastModified = LocalDateTime.now();
    }
}
