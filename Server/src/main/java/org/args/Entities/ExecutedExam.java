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


}
