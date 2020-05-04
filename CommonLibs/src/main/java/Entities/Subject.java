package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectId;

    private static int subjectQuestionCounter = 0;

    public Subject(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getSubjectQuestionCounter() {
        return subjectQuestionCounter;
    }

    public void updateSubjectQuestionCounter() {
        ++Subject.subjectQuestionCounter;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


}
