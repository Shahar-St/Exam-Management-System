package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Subject examSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "exam_id")
    private Course examCourse;

    @ManyToMany(mappedBy = "containingExams")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<Question> examQuestionsList;

    private List<Double> examQuestionsScores;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    private String examId;

    private int examDuration; // in minutes

    private String examDescription;

    private String teacherPrivateNotes; // only for the teacher

    public Exam() {
        this.examQuestionsList = new ArrayList<>();
        this.examQuestionsScores = new ArrayList<>();
    }

    public Exam(Subject examSubject, Course examCourse, Teacher author, int examDuration, String examDescription, String teacherPrivateNotes) {
        this.examSubject = examSubject;
        this.examCourse = examCourse;
        this.author = author;
        this.examDuration = examDuration;
        this.examDescription = examDescription;
        this.teacherPrivateNotes = teacherPrivateNotes;
        this.examQuestionsList = new ArrayList<>();
        this.examQuestionsScores = new ArrayList<>();
    }

    public Subject getExamSubject() {
        return examSubject;
    }

    protected void setExamSubject(Subject examSubject) {
        this.examSubject = examSubject;
    }

    public Course getExamCourse() {
        return examCourse;
    }

    protected void setExamCourse(Course examCourse) {
        this.examCourse = examCourse;
    }

    public List<Question> getExamQuestionsList() {
        return examQuestionsList;
    }

    protected void addExamQuestion(Question question, double score) {
        this.examQuestionsList.add(question);
        this.examQuestionsScores.add(score);
    }

    public Teacher getAuthor() {
        return author;
    }

    protected void setAuthor(Teacher author) {
        this.author = author;
    }

    public String getExamId() {
        return examId;
    }

    protected void setExamId(String examId) {
        this.examId = examId;
    }

    public int getExamDuration() {
        return examDuration;
    }

    protected void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public String getExamDescription() {
        return examDescription;
    }

    protected void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }

    public String getTeacherPrivateNotes() {
        return teacherPrivateNotes;
    }

    protected void setTeacherPrivateNotes(String teacherPrivateNotes) {
        this.teacherPrivateNotes = teacherPrivateNotes;
    }

    public List<Double> getExamQuestionsScores() {
        return examQuestionsScores;
    }

    public String getSerialExamId() {
        return this.examSubject.getSubjectId() + this.examCourse.getCourseId() + this.examId;
    }
}
