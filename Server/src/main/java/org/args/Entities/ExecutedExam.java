package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExecutedExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    @ManyToMany(mappedBy = "containedInExecutedExams")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    private List<Question> questionsList = new ArrayList<>();

    @ElementCollection
    private List<Double> questionsScores = new ArrayList<>();

    private String examId;
    private int grade = 0;
    private int duration; // exam duration in minutes
    private String executedExamDescription, teacherPrivateNotes; // teacherPrivateNotes only for the teacher

    //Group c'tors
    public ExecutedExam() {
    }

    public ExecutedExam(Exam exam, Student student) {

        exam.getCourse().addExecutedExam(this);
        for (Question question : exam.getQuestionsList())
            question.addExecutedExam(this);

        this.questionsScores.addAll(exam.getQuestionsScores());

        exam.getAuthor().addExecutedExam(this);
        this.examId = exam.getId();
        this.duration = exam.getDurationInMinutes();
        this.executedExamDescription = exam.getDescription();
        this.teacherPrivateNotes = exam.getTeacherPrivateNotes();
        student.addExecutedExam(this);

        if (student.getExtensionEligible())
            setOverTime();
    }

    //Group setters and getters
    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {

        this.student = student;
        if (!student.getExecutedExamsList().contains(this))
            student.addExecutedExam(this);
    }

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {

        this.course = course;
        if (!course.getExecutedExamsList().contains(this))
            course.addExecutedExam(this);
    }

    public Teacher getAuthor() {
        return author;
    }
    public void setAuthor(Teacher author) {

        this.author = author;
        if (!author.getExecutedExamsList().contains(this))
            author.addExecutedExam(this);
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }
    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public List<Double> getQuestionsScores() {
        return questionsScores;
    }
    public void setQuestionsScores(List<Double> questionsScores) {
        this.questionsScores = questionsScores;
    }

    public String getExamId() {
        return examId;
    }
    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getExecutedExamDescription() {
        return executedExamDescription;
    }
    public void setExecutedExamDescription(String description) {
        this.executedExamDescription = description;
    }

    public String getTeacherPrivateNotes() {
        return teacherPrivateNotes;
    }
    public void setTeacherPrivateNotes(String teacherPrivateNotes) {
        this.teacherPrivateNotes = teacherPrivateNotes;
    }

    public void setOverTime() {
        this.duration += 0.25 * this.duration;
    }
}
