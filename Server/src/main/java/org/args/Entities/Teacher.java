package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User {

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjectsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Question> questionsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Course> coursesList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Exam> examsList = new ArrayList<>();

    //Group c'tors
    public Teacher() {
    }

    public Teacher(int socialId, String firstName, String lastName, String password, String userName) {
        super(socialId, firstName, lastName, password, userName);
    }

    //Group adders and removers
    public void addSubject(Subject subject) {

        if (!subjectsList.contains(subject))
            subjectsList.add(subject);

        if (!subject.getTeachersList().contains(this))
            subject.getTeachersList().add(this);
    }

    public void addCourse(Course course) {
        if (!this.coursesList.contains(course))
        {
            this.coursesList.add(course);
            course.setTeacher(this);
        }
    }

    public void addExam(Exam exam) {
        if (!this.examsList.contains(exam))
        {
            this.examsList.add(exam);
            exam.setAuthor(this);
        }
    }

    public void addQuestion(Question question) {
        if (!this.questionsList.contains(question))
        {
            this.questionsList.add(question);
            question.setAuthor(this);
        }
    }

    //Group setters and getters
    public List<Subject> getSubjectsList() {
        return subjectsList;
    }
    public void setSubjectsList(List<Subject> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }
    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }
    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public List<Exam> getExamsList() {
        return examsList;
    }
    public void setExamsList(List<Exam> examsList) {
        this.examsList = examsList;
    }
}
