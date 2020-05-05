package Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User {

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ExecutedExam> executedExamList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Course> studentCourseList;

    private Boolean isExtensionEligible=false;

    public Student() {
        super();
        this.executedExamList = new ArrayList<>();
        this.studentCourseList = new ArrayList<>();
    }

    public Student(int socialId, String firstName, String lastName, String password, String userName, Boolean isExtensionEligible) {
        super(socialId, firstName, lastName, password, userName);
        this.executedExamList = new ArrayList<>();
        this.studentCourseList = new ArrayList<>();
        this.isExtensionEligible = isExtensionEligible;
    }

    public List<ExecutedExam> getExecutedExamList() {
        return executedExamList;
    }

    public void addStudentExam(ExecutedExam executedExam) {
        if (!this.executedExamList.contains(executedExam)) {
            this.executedExamList.add(executedExam);
        }

    }

    public List<Course> getCourseList() {
        return studentCourseList;
    }

    public void addCourse(Course course) {
        if (!this.studentCourseList.contains(course)) {

            this.studentCourseList.add(course);
        }
    }

    public Boolean getExtensionEligible() {
        return isExtensionEligible;
    }

    public void setExtensionEligible(Boolean extensionEligible) {
        isExtensionEligible = extensionEligible;
    }
}
