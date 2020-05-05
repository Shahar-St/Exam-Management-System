package Entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User {

    @OneToMany
    private List<StudentExam> studentExamList;
    @OneToMany
    private List<Course> studentCourseList;

    private Boolean isExtensionEligible;

    public Student() {
        super();
        this.studentExamList = new ArrayList<>();
        this.studentCourseList = new ArrayList<>();
        this.isExtensionEligible = false;
    }

    public Student(int socialId, String firstName, String lastName, String password, String userName, Boolean isExtensionEligible) {
        super(socialId, firstName, lastName, password, userName);
        this.studentExamList = new ArrayList<>();
        this.studentCourseList = new ArrayList<>();
        this.isExtensionEligible = isExtensionEligible;
    }

    public List<StudentExam> getStudentExamList() {
        return studentExamList;
    }

    public void addStudentExam(StudentExam studentExam) {
        this.studentExamList.add(studentExam);
    }

    public List<Course> getStudentCourseList() {
        return studentCourseList;
    }

    public void addStudentCourseList(Course course) {
        this.studentCourseList.add(course);
    }

    public Boolean getExtensionEligible() {
        return isExtensionEligible;
    }

    public void setExtensionEligible(Boolean extensionEligible) {
        isExtensionEligible = extensionEligible;
    }
}
