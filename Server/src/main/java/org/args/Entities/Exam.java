package org.args.Entities;

import LightEntities.LightExam;
import LightEntities.LightQuestion;
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
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ConcreteExam> concreteExamsList = new ArrayList<>();

    @ManyToMany(mappedBy = "containedInExams")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    private final List<Question> questionsList = new ArrayList<>();

    @ElementCollection
    private List<Double> questionsScores = new ArrayList<>();

    private int durationInMinutes;
    private String title;
    private String studentNotes;
    private String teacherNotes; // only for the teacher
    private LocalDateTime lastModified;


    //Group c'tors
    public Exam() {
    }

    public Exam(Course course, Teacher author, int durationInMinutes, String title, String studentNotes,
                String teacherNotes, List<Question> questionsList, List<Double> questionsScores) {

        setCourse(course);
        setAuthor(author);
        this.durationInMinutes = durationInMinutes;
        this.title = title;
        this.studentNotes = studentNotes;
        this.teacherNotes = teacherNotes;
        this.setQuestionsList(questionsList);
        this.questionsScores.addAll(questionsScores);
        setLastModified();

        DecimalFormat decimalFormat = new DecimalFormat("00");
        this.id = course.getSubject().getId() + course.getId() +
                decimalFormat.format(course.getAvailableExamCodes().remove(0));
    }

    @PreRemove
    private void preRemove() {
        for (Question question : questionsList)
            question.getContainedInExams().remove(this);
    }

    //Group adders and removers
    public void addQuestion(Question question) {

        if (!questionsList.contains(question))
            questionsList.add(question);

        if (!question.getContainedInExams().contains(this))
            question.getContainedInExams().add(this);
    }

    public void addConcreteExam(ConcreteExam concreteExam) {
        if (!concreteExamsList.contains(concreteExam))
            concreteExamsList.add(concreteExam);

        if (concreteExam.getExam() != this)
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

        for(Question question : this.questionsList)
            question.getContainedInExams().remove(this);

        this.questionsList.clear();

        for (Question question : questionsList)
            this.addQuestion(question);
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

    public LightExam createLightExam() {
        List<LightQuestion> lightQuestionsList = new ArrayList<>();

        for (Question question : this.getQuestionsList())
            lightQuestionsList.add(question.createLightQuestion());

        return new LightExam(this.id, this.author.getUserName(), lightQuestionsList, new ArrayList<>(this.questionsScores),
                this.durationInMinutes, this.title, this.teacherNotes, this.studentNotes);
    }
}
