package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Subject examSubject;

    private Course examCourse;

    private List<Question> examQuestionsList;

    private Teacher author;

    private String examId;

    private int examGrade;

    private int examDuration; // in minutes

    private String examDescription;

    private String teacherPrivateDescription; // only for the teacher

    public Exam() {
    }

    public Exam(Subject examSubject, Course examCourse, Teacher author, int examDuration, String examDescription, String teacherPrivateDescription) {
        this.examSubject = examSubject;
        this.examCourse = examCourse;
        this.author = author;
        this.examDuration = examDuration;
        this.examDescription = examDescription;
        this.teacherPrivateDescription = teacherPrivateDescription;
        this.examQuestionsList = new LinkedList<>();
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

    protected void addExamQuestion(Question question) {
        this.examQuestionsList.add(question);
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

    public int getExamGrade() {
        return examGrade;
    }

    protected void setExamGrade(int examGrade) {
        this.examGrade = examGrade;
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

    public String getTeacherPrivateDescription() {
        return teacherPrivateDescription;
    }

    protected void setTeacherPrivateDescription(String teacherPrivateDescription) {
        this.teacherPrivateDescription = teacherPrivateDescription;
    }
}
