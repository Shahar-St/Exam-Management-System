package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.sql.Struct;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
public class Course {

    //to remove
    private static int courseQuestionCounter = 0;

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String name;
    private final Queue<Integer> availableQuestionNumbers = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Exam> examsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Question> questionsList = new ArrayList<>();

    @ManyToMany
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentsList = new ArrayList<>();

    //Group c'tors
    public Course() { }

    public Course(String id,String name, Subject subject, Teacher teacher) {

        // make id parameter int
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;

        for (int i = 0; i < 1000; i++)  // max questions per course
            availableQuestionNumbers.add(i);
    }

    //Group adders and removers
    public void addExam(Exam exam) {
        if (!examsList.contains(exam))
        {
            examsList.add(exam);
            exam.setCourse(this);
        }
    }

    public void addQuestion(Question question) {
        if (!questionsList.contains(question))
        {
            questionsList.add(question);
            question.setCourse(this);
        }
    }

    public void addStudent(Student student) {

        if (!studentsList.contains(student))
            studentsList.add(student);

        if (!student.getCoursesList().contains(this))
            student.getCoursesList().add(this);
    }

    //Group setters and getters
    public Queue<Integer> getAvailableQuestionNumbers() { return availableQuestionNumbers; }
    public int getCourseQuestionCounter() { return courseQuestionCounter; }
    protected void updateCourseQuestionCounter() { courseQuestionCounter++; }



    public String getId() { return id; }
    protected void setId(String courseId) { this.id = courseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public List<Exam> getExamsList() { return examsList; }
    public void setExamsList(List<Exam> examsList) { this.examsList = examsList; }

    public List<Question> getQuestionsList() { return questionsList; }
    public void setQuestionsList(List<Question> questionsList) { this.questionsList = questionsList; }

    public List<Student> getStudentsList() { return studentsList; }
    public void setStudentsList(List<Student> studentsList) { this.studentsList = studentsList; }
}
