package Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    private static int courseQuestionCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "subject_id")
    private Subject courseSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "examCourse")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Exam> courseExamList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionCourse")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Question> courseQuestionList;

    @ManyToMany
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> courseStudentList;

    public Course() {
        this.courseExamList = new ArrayList<>();
        this.courseStudentList = new ArrayList<>();
        this.courseQuestionList = new ArrayList<>();
    }

    public Course(String courseId, Subject courseSubject, Teacher teacher) {
        this.courseId = courseId;
        this.courseSubject = courseSubject;
        this.teacher = teacher;
        this.courseExamList = new ArrayList<>();
        this.courseStudentList = new ArrayList<>();
        this.courseQuestionList = new ArrayList<>();
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

    public void addStudent(Student student) {
        this.courseStudentList.add(student);
    }
}
