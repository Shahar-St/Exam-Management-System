package org.args.Entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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

    //Group c'tors

    public ConcreteExam() { }

    public ConcreteExam(Exam exam, Teacher tester) {
        this.setExam(exam);
        this.setTester(tester);
    }

    public void addExecutedExam(ExecutedExam executedExam) {

        if (!executedExamsList.contains(executedExam))
            executedExamsList.add(executedExam);

        if (executedExam.getConcreteExam() != this)
            executedExam.setConcreteExam(this);
    }

    //Group adders and removers
    public int getId() {
        return id;
    }

    public Teacher getTester() {
        return tester;
    }
    public void setTester(Teacher tester){
        this.tester = tester;
        if (!tester.getConcreteExamsList().contains(this))
            tester.addConcreteExam(this);
    }

    public Exam getExam() { return exam; }
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

}
