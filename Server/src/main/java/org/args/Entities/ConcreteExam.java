package org.args.Entities;

import LightEntities.LightExam;
import LightEntities.LightQuestion;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConcreteExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher tester;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "concreteExam")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<ExecutedExam> executedExamsList = new ArrayList<>();

    private String examCode;
    private int finishedOnTime = 0;
    private int unfinishedOnTime = 0;
    private LocalDateTime examForExecutionInitDate;

    //Group c'tors
    public ConcreteExam() {
    }

    public ConcreteExam(Exam exam, Teacher tester, String examCode) {
        this.setExam(exam);
        this.setTester(tester);
        this.examCode = examCode;
        this.examForExecutionInitDate = LocalDateTime.now();
    }

    public void addExecutedExam(ExecutedExam executedExam) {

        if (!executedExamsList.contains(executedExam))
            executedExamsList.add(executedExam);

        if (executedExam.getConcreteExam() != this)
            executedExam.setConcreteExam(this);
    }

    public synchronized void addFinishedOnTime() {
        finishedOnTime++;
    }

    public synchronized void addUnfinishedOnTime() {
        unfinishedOnTime++;
    }

    //Group adders and removers
    public int getId() {
        return id;
    }

    public Teacher getTester() {
        return tester;
    }
    public void setTester(Teacher tester) {
        this.tester = tester;
        if (!tester.getConcreteExamsList().contains(this))
            tester.addConcreteExam(this);
    }

    public Exam getExam() {
        return exam;
    }
    public void setExam(Exam exam) {
        this.exam = exam;
        if (!exam.getConcreteExamsList().contains(this))
            exam.addConcreteExam(this);
    }

    public List<ExecutedExam> getExecutedExamsList() {
        return executedExamsList;
    }
    public void setExecutedExamsList(List<ExecutedExam> executedExamsList) {
        this.executedExamsList = executedExamsList;
    }

    public String getExamCode() {
        return examCode;
    }

    public LightExam createLightExam() {
        List<LightQuestion> lightQuestionsList = new ArrayList<>();

        for (Question question : exam.getQuestionsList())
            lightQuestionsList.add(question.createLightQuestion());

        return new LightExam(String.valueOf(this.id), exam.getAuthor().getUserName(), lightQuestionsList,
                new ArrayList<>(exam.getQuestionsScores()), exam.getDurationInMinutes(),
                exam.getTitle(), exam.getStudentNotes());
    }

    public int getFinishedOnTime() {
        return finishedOnTime;
    }
    public void setFinishedOnTime(int finishedOnTime) {
        this.finishedOnTime = finishedOnTime;
    }
    public int getUnfinishedOnTime() {
        return unfinishedOnTime;
    }
    public void setUnfinishedOnTime(int unfinishedOnTime) {
        this.unfinishedOnTime = unfinishedOnTime;
    }

    public LocalDateTime getExamForExecutionInitDate() {
        return examForExecutionInitDate;
    }

    public void setExamForExecutionInitDate(LocalDateTime examForExecutionInitDate) {
        this.examForExecutionInitDate = examForExecutionInitDate;
    }
}
