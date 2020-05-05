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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ExecutedExam> executedExamsList = new ArrayList<>();

    @ManyToMany(mappedBy = "studentsList")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> coursesList = new ArrayList<>();

    private Boolean isExtensionEligible;

    //Group c'tors
    public Student() {
    }
    public Student(int socialId, String firstName, String lastName, String password,
                   String userName, Boolean isExtensionEligible) {
        super(socialId, firstName, lastName, password, userName);
        this.isExtensionEligible = isExtensionEligible;
    }

    //Group adders and removers
    public void addStudentExam(ExecutedExam executedExam) {
        if (!this.executedExamsList.contains(executedExam))
        {
            this.executedExamsList.add(executedExam);
        }
    }

    public void addCourse(Course course) {
        if (!this.coursesList.contains(course))
        {

            this.coursesList.add(course);
        }
    }

    //Group setters and getters
    public List<ExecutedExam> getExecutedExamsList() {
        return executedExamsList;
    }
    public void setExecutedExamsList(List<ExecutedExam> executedExamsList) {
        this.executedExamsList = executedExamsList;
    }

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
}
