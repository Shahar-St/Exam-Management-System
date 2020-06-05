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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tester")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ConcreteExam> concreteExamsList = new ArrayList<>();

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
        if (!coursesList.contains(course))
            coursesList.add(course);

        if (course.getTeacher() != this)
            course.setTeacher(this);
    }

    public void addExam(Exam exam) {
        if (!examsList.contains(exam))
            examsList.add(exam);

        if (exam.getAuthor() != this)
            exam.setAuthor(this);
    }

    public void addQuestion(Question question) {
        if (!questionsList.contains(question))
            questionsList.add(question);

        if (question.getAuthor() != this)
            question.setAuthor(this);
    }

    public void addConcreteExam(ConcreteExam concreteExam) {
        if (!concreteExamsList.contains(concreteExam))
            concreteExamsList.add(concreteExam);

        if (concreteExam.getTester() != this)
            concreteExam.setTester(this);
    }

    public Question createQuestion(String questionContent, List<String> answersArray, int correctAnswer, Course course){
        Question question = new Question(questionContent, answersArray, correctAnswer, course, this);
        //this.questionsList.add(question);
        return question;
    }

    public Exam createExam(Course course, int durationInMinutes, String title, String studentNotes,
                           String teacherNotes, List<Question> questionsList, List<Double> questionsScores)
    {
        Exam exam = new Exam(course, this, durationInMinutes, title, studentNotes, teacherNotes,
                questionsList, questionsScores);
        this.examsList.add(exam);
        return exam;
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

    public List<ConcreteExam> getConcreteExamsList() {
        return concreteExamsList;
    }
    public void setConcreteExamsList(List<ConcreteExam> concreteExamsList) {
        this.concreteExamsList = concreteExamsList;
    }
}
