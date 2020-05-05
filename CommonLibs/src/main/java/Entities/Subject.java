package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectId;

    private List<Teacher> subjectTeachersList;

    public Subject() {
        this.subjectTeachersList = new ArrayList<>();
    }


    public Subject(String subjectId, List<Teacher> subjectTeachersList) {
        this.subjectId = subjectId;
        this.subjectTeachersList = subjectTeachersList;
        this.subjectTeachersList = new ArrayList<>();
    }

    public Subject(String subjectId) {
        this.subjectId = subjectId;
    }


    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


}
