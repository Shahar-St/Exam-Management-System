package org.args.Entities;

import LightEntities.LightExam;
import LightEntities.LightQuestion;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<ConcreteExam> concreteExamsList = new ArrayList<>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
//    @Cascade(CascadeType.SAVE_UPDATE)
//    private List<ExecutedExam> executedExamsList = new ArrayList<>();

    @ManyToMany(mappedBy = "containedInExams")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    private List<Question> questionsList = new ArrayList<>();

    @ElementCollection
    private List<Double> questionsScores = new ArrayList<>();

    private int durationInMinutes;
    private String title;
    private String studentNotes;
    private String teacherNotes; // only for the teacher
    private LocalDateTime lastModified;


    //Group c'tors
    public Exam() { }

    public Exam(Course course, Teacher author, int durationInMinutes, String title, String studentNotes,
                String teacherNotes, List<Question> questionsList, List<Double> questionsScores) {

        setCourse(course);
        setAuthor(author);
        this.durationInMinutes = durationInMinutes;
        this.title = title;
        this.studentNotes = studentNotes;
        this.teacherNotes = teacherNotes;
        this.questionsList.addAll(questionsList);
        this.questionsScores.addAll(questionsScores);
        setLastModified();

        //handle max size
        DecimalFormat decimalFormat = new DecimalFormat("00");
        this.id = course.getSubject().getId() + course.getId() +
                decimalFormat.format(course.getAvailableExamCodes().poll());
    }

    public Exam(Exam exam) {
        this(exam.course, exam.author, exam.durationInMinutes, exam.title, exam.studentNotes, exam.teacherNotes,
                exam.questionsList, exam.questionsScores);
    }

    //Group adders and removers
    public void addQuestion(Question question) {

        if (!questionsList.contains(question))
            questionsList.add(question);

        if (!question.getContainedInExams().contains(this))
            question.getContainedInExams().add(this);
    }

//    public void addExecutedExam(ExecutedExam executedExam) {
//        if (!executedExamsList.contains(executedExam))
//            executedExamsList.add(executedExam);
//
//        if (executedExam.getExam() != this)
//            executedExam.setExam(this);
//    }

    public void addConcreteExam(ConcreteExam concreteExam) {
        if (!concreteExamsList.contains(concreteExam))
            concreteExamsList.add(concreteExam);

        if(concreteExam.getExam() != this)
            concreteExam.setExam(this);
    }

    //Group setters and getters
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {

        this.course = course;
        if (!course.getExamsList().contains(this))
            course.addExam(this);
    }

    public Teacher getAuthor() {
        return author;
    }

    public void setAuthor(Teacher author) {

        this.author = author;
        if (!author.getExamsList().contains(this))
            author.addExam(this);
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public String getId() {
        return id;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int duration) {
        this.durationInMinutes = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String privateNotes) {
        teacherNotes = privateNotes;
    }

    public List<Double> getQuestionsScores() {
        return questionsScores;
    }

    public void setQuestionsScores(List<Double> questionsScores) {
        this.questionsScores = questionsScores;
    }

//    public List<ExecutedExam> getExecutedExamsList() {
//        return executedExamsList;
//    }
//
//    public void setExecutedExamsList(List<ExecutedExam> executedExamsList) {
//        this.executedExamsList = executedExamsList;
//    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
    public void setLastModified() {
        this.lastModified = LocalDateTime.now();
    }

    public List<ConcreteExam> getConcreteExamsList() {
        return concreteExamsList;
    }
    public void setConcreteExamsList(List<ConcreteExam> concreteExamsList) {
        this.concreteExamsList = concreteExamsList;
    }



    @Override
    public LightExam clone() throws CloneNotSupportedException {
        super.clone();
        List<LightQuestion> lightQuestionsList = new ArrayList<>();
        ;
        for (Question question : this.getQuestionsList())
            lightQuestionsList.add(question.clone());

        return new LightExam(this.id, this.author.getUserName(), lightQuestionsList, this.questionsScores,
                this.durationInMinutes, this.title, this.teacherNotes, this.studentNotes);
    }
}
