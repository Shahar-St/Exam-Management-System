package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExecutedExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "student_id")
    private Student student;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Cascade(CascadeType.SAVE_UPDATE)
//    @JoinColumn(name = "course_id")
//    private Course course;

     @ManyToOne(fetch = FetchType.LAZY)
     @Cascade(CascadeType.SAVE_UPDATE)
     @JoinColumn(name = "ConcreteExam_id")
     private ConcreteExam exam;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Cascade(CascadeType.SAVE_UPDATE)
//    @JoinColumn(name = "teacher_id")
//    private Teacher author;

//    @ManyToMany(mappedBy = "containedInExecutedExams")
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
//    private List<Question> questionsList = new ArrayList<>();

//    @ElementCollection
//    private List<Double> questionsScores = new ArrayList<>();

    @ElementCollection
    private List<Integer> answersByStudent= new ArrayList<>();

//    private String examId;
    private String reasonsForChangeGrade;
    private String commentsAfterCheck;
    private int grade = 0;
    private int duration; // exam duration in minutes
//    private String executedExamDescription, teacherPrivateNotes; // teacherPrivateNotes only for the teacher

    //Group c'tors
    public ExecutedExam() { }

    public ExecutedExam(ConcreteExam exam, Student student, String commentsAfterCheck,
                        List<Integer> answersByStudent, String reasonsForChangeGrade) {

        this.setExam(exam);
        this.setStudent(student);
        this.commentsAfterCheck = commentsAfterCheck;
        this.reasonsForChangeGrade = reasonsForChangeGrade;
        this.answersByStudent = answersByStudent;
        if (student.getExtensionEligible())
            setOverTime();
        else
            this.duration = exam.getDurationInMinutes();

//        this.setCourse(exam.getCourse());
//        for (Question question : exam.getQuestionsList())
//            this.addQuestion(question);
//        this.questionsScores.addAll(exam.getQuestionsScores());

//        this.setAuthor(exam.getAuthor());
//        this.examId = exam.getId();
//          this.duration = exam.getDurationInMinutes();
//        this.executedExamDescription = exam.getStudentNotes();
//        this.teacherPrivateNotes = exam.getTeacherNotes();


    }

    //Group adders and removers

//    public void addQuestion(Question question) {
//        if (!questionsList.contains(question))
//            questionsList.add(question);
//
//        if (!question.getContainedInExecutedExams().contains(this))
//            question.getContainedInExecutedExams().add(this);
//    }

    //Group setters and getters
    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {

        this.student = student;
        if (!student.getExecutedExamsList().contains(this))
            student.addExecutedExam(this);
    }

    public ConcreteExam getExam() {
        return exam;
    }
    public void setExam(ConcreteExam exam) {

        this.exam = exam;
        if (!exam.getExecutedExamsList().contains(this))
            exam.addExecutedExam(this);
    }
//
//    public Teacher getAuthor() {
//        return author;
//    }
//    public void setAuthor(Teacher author) {
//
//        this.author = author;
//        if (!author.getExecutedExamsList().contains(this))
//            author.addExecutedExam(this);
//    }

//    public List<Question> getQuestionsList() {
//        return questionsList;
//    }
//    public void setQuestionsList(List<Question> questionsList) {
//        this.questionsList = questionsList;
//    }
//
//    public List<Double> getQuestionsScores() {
//        return questionsScores;
//    }
//    public void setQuestionsScores(List<Double> questionsScores) {
//        this.questionsScores = questionsScores;
//    }
//
//    public String getExamId() {
//        return examId;
//    }
//    public void setExamId(String examId) {
//        this.examId = examId;
//    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

//    public String getExecutedExamDescription() {
//        return executedExamDescription;
//    }
//    public void setExecutedExamDescription(String description) {
//        this.executedExamDescription = description;
//    }
//
//    public String getTeacherPrivateNotes() {
//        return teacherPrivateNotes;
//    }
//    public void setTeacherPrivateNotes(String teacherPrivateNotes) {
//        this.teacherPrivateNotes = teacherPrivateNotes;
//    }

    public void setOverTime() {
        this.duration += 0.25 * this.duration;
    }
}
