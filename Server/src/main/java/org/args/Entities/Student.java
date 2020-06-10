package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ExecutedExam> executedExamsList = new ArrayList<>();

    @ManyToMany(mappedBy = "studentsList")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    private List<Course> coursesList = new ArrayList<>();

    private int currentlyExecutedID = -1;

    private Boolean isExtensionEligible;

    //Group c'tors
    public Student() { }

    public Student(int socialId, String firstName, String lastName, String password,
                   String userName, Boolean isExtensionEligible) {
        super(socialId, firstName, lastName, password, userName);
        this.isExtensionEligible = isExtensionEligible;
    }

    //Group adders and removers
    public void addExecutedExam(ExecutedExam executedExam) {
        if (!executedExamsList.contains(executedExam))
            executedExamsList.add(executedExam);

        if(executedExam.getStudent() != this)
            executedExam.setStudent(this);
    }

    public void addCourse(Course course) {
        if (!coursesList.contains(course))
            coursesList.add(course);

        if (!course.getStudentsList().contains(this))
            course.getStudentsList().add(this);
    }

    //Group setters and getters
    public List<ExecutedExam> getExecutedExamsList() {
        return executedExamsList;
    }
    public void setExecutedExamsList(List<ExecutedExam> execExamsList) { executedExamsList = execExamsList; }

    public List<Course> getCoursesList() {
        return coursesList;
    }
    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public Boolean getExtensionEligible() {
        return isExtensionEligible;
    }
    public void setExtensionEligible(Boolean extensionEligible) {
        isExtensionEligible = extensionEligible;
    }

    public int getCurrentlyExecutedID() { return currentlyExecutedID; }

    public void setCurrentlyExecutedID(int idExecutedExamCurrent) {
        this.currentlyExecutedID = idExecutedExamCurrent;
    }
}
