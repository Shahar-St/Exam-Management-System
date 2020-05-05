package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
public class Course {

    private static int courseQuestionCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseId;
    private Queue<Integer> availableQuestionNumbers = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "subject_id")
    private Subject subject;

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
    private List<Student> studentsList;

    public Course() {
        this.courseExamList = new ArrayList<>();
        this.studentsList = new ArrayList<>();
        this.courseQuestionList = new ArrayList<>();
    }

    public Course(String courseId, Subject subject, Teacher teacher) {
        this.courseId = courseId;
        this.subject = subject;
        this.teacher = teacher;
        this.courseExamList = new ArrayList<>();
        this.studentsList = new ArrayList<>();
        this.courseQuestionList = new ArrayList<>();

        for (int i = 0; i < 1000; i++)
            availableQuestionNumbers.add(i);

    }

    public Queue<Integer> getAvailableQuestionNumbers() {
        return availableQuestionNumbers;
    }
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void addStudent(Student student) {
        this.studentsList.add(student);
    }
}
