package Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectId;

    @ManyToMany(mappedBy = "teacherSubjectList")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<Teacher> subjectTeachersList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseSubject")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Course> subjectCoursesList;

    public Subject() {
        this.subjectTeachersList = new ArrayList<>();
        this.subjectCoursesList = new ArrayList<>();
    }

    public Subject(String subjectId) {
        this.subjectId = subjectId;
        this.subjectTeachersList = new ArrayList<>();
        this.subjectCoursesList = new ArrayList<>();
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
