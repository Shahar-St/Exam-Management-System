package Entities;


import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StudentExam {

    private Subject studentExamSubject;

    private Course studentExamCourse;

    private List<Question> studentExamQuestionsList;

    private List<Double>  studentExamQuestionsScores;

    private Teacher author;

    private String studentExamId;

    private int studentExamGrade;

    private int studentExamDuration; // in minutes

    private String studentExamDescription;

    private String teacherPrivateNotes; // only for the teacher

    public StudentExam(Exam exam) {

        this.studentExamSubject = exam.getExamSubject();
        this.studentExamCourse = exam.getExamCourse();
        this.studentExamQuestionsList = new ArrayList<>();
        this.studentExamQuestionsList.addAll(exam.getExamQuestionsList());
        this.studentExamQuestionsScores = new ArrayList<>();
        this.studentExamQuestionsScores.addAll(exam.getExamQuestionsScores());
        this.author = exam.getAuthor();
        this.studentExamId = exam.getExamId();
        this.studentExamGrade = 0;
        this.studentExamDuration = exam.getExamDuration();
        this.studentExamDescription = exam.getExamDescription();
        this.teacherPrivateNotes = exam.getTeacherPrivateNotes();

    }

    public Subject getStudentExamSubject() {
        return studentExamSubject;
    }

    public Course getStudentExamCourse() {
        return studentExamCourse;
    }

    public List<Question> getStudentExamQuestionsList() {
        return studentExamQuestionsList;
    }

    public List<Double> getStudentExamQuestionsScores() {
        return studentExamQuestionsScores;
    }

    public Teacher getAuthor() {
        return author;
    }

    public String getStudentExamId() {
        return studentExamId;
    }

    public int getStudentExamGrade() {
        return studentExamGrade;
    }

    public int getStudentExamDuration() {
        return studentExamDuration;
    }

    public String getStudentExamDescription() {
        return studentExamDescription;
    }

    public String getTeacherPrivateNotes() {
        return teacherPrivateNotes;
    }

    public void setOverTime()
    {
        this.studentExamDuration+= 0.25*this.studentExamDuration;
    }
}
