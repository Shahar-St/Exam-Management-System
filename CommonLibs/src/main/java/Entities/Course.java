package Entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    private static int courseQuestionCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseId;

    private Subject courseSubject;

    private Teacher teacher;

    private List<Exam> courseExamList;

    private List<Student> courseStudentList;

    public Course() {
        this.courseExamList = new ArrayList<>();
        this.courseStudentList = new ArrayList<>();
    }

    public Course(String courseId, Subject courseSubject, Teacher teacher) {
        this.courseId = courseId;
        this.courseSubject = courseSubject;
        this.teacher = teacher;
        this.courseExamList = new ArrayList<>();
        this.courseStudentList = new ArrayList<>();
    }

    public Subject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(Subject courseSubject) {
        this.courseSubject = courseSubject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getCourseQuestionCounter() {
        return courseQuestionCounter;
    }


    protected void updateCourseQuestionCounter() {
        courseQuestionCounter++;
    }

    public String getCourseId() {
        return courseId;
    }

    protected void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Exam> getCourseExamList() {
        return courseExamList;
    }

    public void addCourseExamList(Exam exam) {
        this.courseExamList.add(exam);
        exam.setExamCourse(this);
    }

    public List<Student> getCourseStudentList() {
        return courseStudentList;
    }

    public void addCourseStudentList(Student student) {
        this.courseStudentList.add(student);
        student.addCourse(this);
    }
}
