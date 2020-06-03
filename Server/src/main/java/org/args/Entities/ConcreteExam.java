package org.args.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConcreteExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Exam exam;

    @ManyToOne
    private Teacher tester;

    @OneToMany
    private List<ExecutedExam> executedExamsList = new ArrayList<>();


    public void addExecutedExam(ExecutedExam executedExam) {// need to implement

    }

    public Teacher getTester() {
        return tester;
    }
    public void setTester(Teacher tester) {   // need to fix
        this.tester = tester;
    }

    public List<ExecutedExam> getExecutedExamsList() {
        return executedExamsList;
    }
    public void setExecutedExamsList(List<ExecutedExam> executedExamsList) {
        this.executedExamsList = executedExamsList;
    }

    public int getDurationInMinutes(){
        return exam.getDurationInMinutes();
    }
}
