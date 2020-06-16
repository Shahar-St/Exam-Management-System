package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.*;

@Entity
public class Course {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String name;

    @ElementCollection
    private final List<Integer> availableQuestionCodes = new LinkedList<>();
    @ElementCollection
    private final List<Integer> availableExamCodes = new LinkedList<>();

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
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentsList = new ArrayList<>();

    //Group c'tors
    public Course() {
    }

    public Course(int id, String name, Subject subject) {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        this.id = decimalFormat.format(id);
        this.name = name;
        this.setSubject(subject);

        for (int i = 0; i < 1000; i++)  // max questions per course
            availableQuestionCodes.add(i);

        for (int i = 0; i < 100; i++)  // max exams per course
            availableExamCodes.add(i);
    }

    //Group adders and removers
    public void addExam(Exam exam) {
        if (!examsList.contains(exam))
            examsList.add(exam);

        if (exam.getCourse() != this)
            exam.setCourse(this);
    }

    public void addQuestion(Question question) {
        if (!questionsList.contains(question))
            questionsList.add(question);

        if (question.getCourse() != this)
            question.setCourse(this);
    }

    public void addStudent(Student student) {
        if (!studentsList.contains(student))
            studentsList.add(student);

        if (!student.getCoursesList().contains(this))
            student.getCoursesList().add(this);
    }

    //Group setters and getters
    public List<Integer> getAvailableQuestionCodes() {
        return availableQuestionCodes;
    }
    public List<Integer> getAvailableExamCodes() {
        return availableExamCodes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {

        this.subject = subject;
        if (!subject.getCoursesList().contains(this))
            subject.addCourse(this);
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {

        this.teacher = teacher;
        if (!teacher.getCoursesList().contains(this))
            teacher.addCourse(this);
    }

    public List<Exam> getExamsList() {
        return examsList;
    }
    public void setExamsList(List<Exam> examsList) {
        this.examsList = examsList;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }
    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }
    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

}