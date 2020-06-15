package org.args.Entities;

import LightEntities.LightExecutedExam;
import LightEntities.LightQuestion;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "concrete_id")
    private ConcreteExam concreteExam;

    @ElementCollection
    private List<Integer> answersByStudent = new ArrayList<>();

    private String reasonsForChangeGrade;
    private String commentsAfterCheck;
    private double grade = 0;
    private int duration; // exam duration in minutes
    private boolean isComputerized = false;
    private boolean checked = false;
    private boolean finishedOnTime = false;
    private boolean isSubmitted = false;
    @Column(length = 3000)
    private byte[] fileBytes;

    //Group c'tors
    public ExecutedExam() {
    }

    public ExecutedExam(ConcreteExam concreteExam, Student student, String commentsAfterCheck,
                        List<Integer> answersByStudent, String reasonsForChangeGrade) {

        this.setConcreteExam(concreteExam);
        this.setStudent(student);
        this.commentsAfterCheck = commentsAfterCheck;
        this.reasonsForChangeGrade = reasonsForChangeGrade;
        this.answersByStudent = answersByStudent;
        this.duration = concreteExam.getExam().getDurationInMinutes();
        if (student.getExtensionEligible())
            setOverTime();
    }

    public ExecutedExam(ConcreteExam concreteExam, Student student) {
        this(concreteExam, student, null, null, null);
    }

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

    public ConcreteExam getConcreteExam() {
        return concreteExam;
    }
    public void setConcreteExam(ConcreteExam concreteExam) {

        this.concreteExam = concreteExam;
        if (!concreteExam.getExecutedExamsList().contains(this))
            concreteExam.addExecutedExam(this);
    }

    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isComputerized() {
        return isComputerized;
    }

    public void setComputerized(boolean computerized) {
        isComputerized = computerized;
    }

    public void setOverTime() {
        this.duration += 0.25 * this.duration;
    }

    public List<Integer> getAnswersByStudent() {
        return answersByStudent;
    }

    public void setAnswersByStudent(List<Integer> answersByStudent) {
        this.answersByStudent = answersByStudent;
    }

    public String getReasonsForChangeGrade() {
        return reasonsForChangeGrade;
    }

    public void setReasonsForChangeGrade(String reasonsForChangeGrade) {
        this.reasonsForChangeGrade = reasonsForChangeGrade;
    }

    public String getCommentsAfterCheck() {
        return commentsAfterCheck;
    }

    public void setCommentsAfterCheck(String commentsAfterCheck) {
        this.commentsAfterCheck = commentsAfterCheck;
    }

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public boolean isFinishedOnTime() {
        return finishedOnTime;
    }
    public void setFinishedOnTime(boolean finishedOnTime) {
        this.finishedOnTime = finishedOnTime;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public LightExecutedExam getLightExecutedExam() {

        List<LightQuestion> lightQuestionsList = new ArrayList<>();
        for (Question question : concreteExam.getExam().getQuestionsList())
            lightQuestionsList.add(question.createLightQuestion());
        List<Double> questionScoreList = new ArrayList<>(concreteExam.getExam().getQuestionsScores());
        LightExecutedExam lightExecutedExam = new LightExecutedExam(concreteExam.getExam().getTitle(), concreteExam.getTester().getUserName(),
                String.valueOf(id), student.getSocialId(), lightQuestionsList, questionScoreList,
                new ArrayList<>(this.answersByStudent), duration, isComputerized, this.grade,
                this.reasonsForChangeGrade, this.commentsAfterCheck);
        lightExecutedExam.setManualExam(getFileBytes());

        return lightExecutedExam;
    }
}
