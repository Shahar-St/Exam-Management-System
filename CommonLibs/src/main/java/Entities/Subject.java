package Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectId;
    @ManyToMany
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

    public List<Teacher> getSubjectTeachersList() {
        return subjectTeachersList;
    }

    public void addTeacher(Teacher teacher) {
        this.subjectTeachersList.add(teacher);
    }
}
