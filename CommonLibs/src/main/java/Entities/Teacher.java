package Entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User {

    @ManyToMany
    private List<Subject> teacherSubjectList;
    @OneToMany
    private List<Course> teacherCourseList;
    @OneToMany
    private List<Exam> teacherExamList;

    public Teacher() {
        this.teacherCourseList = new ArrayList<>();
        this.teacherSubjectList = new ArrayList<>();
        this.teacherExamList = new ArrayList<>();
    }

    public Teacher(int socialId, String firstName, String lastName, String password, String userName) {
        super(socialId, firstName, lastName, password, userName);
        this.setPermissionLevel("TeacherPermission");
        this.teacherCourseList = new ArrayList<>();
        this.teacherSubjectList = new ArrayList<>();
        this.teacherExamList = new ArrayList<>();
    }

    public List<Subject> getSubjectList() {
        return teacherSubjectList;
    }

    public void addSubject(Subject subject) {
        this.teacherSubjectList.add(subject);
        subject.addTeacher(this);
    }

    public List<Course> getCourseList() {
        return teacherCourseList;
    }

    public void addCourse(Course course) {
        this.teacherCourseList.add(course);
        course.setTeacher(this);
    }

    public List<Exam> getExamList() {
        return teacherExamList;
    }

    public void addExam(Exam exam) {
        this.teacherExamList.add(exam);
        exam.setAuthor(this);
    }
}
