package Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User {

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE})
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> teacherSubjectList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Question> teacherQuestionList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Course> teacherCourseList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Exam> teacherExamList;

    public Teacher() {
        this.teacherCourseList = new ArrayList<>();
        this.teacherSubjectList = new ArrayList<>();
        this.teacherExamList = new ArrayList<>();
        this.teacherQuestionList = new ArrayList<>();
    }

    public Teacher(int socialId, String firstName, String lastName, String password, String userName) {
        super(socialId, firstName, lastName, password, userName);
        this.setPermissionLevel("TeacherPermission");
        this.teacherCourseList = new ArrayList<>();
        this.teacherSubjectList = new ArrayList<>();
        this.teacherExamList = new ArrayList<>();
        this.teacherQuestionList = new ArrayList<>();
    }

    public List<Subject> getSubjectList() {
        return teacherSubjectList;
    }

    public void addSubject(Subject subject) {
        if (!this.teacherSubjectList.contains(subject)) {
            this.teacherSubjectList.add(subject);
            subject.addTeacher(this);
        }
    }

    public List<Course> getCourseList() {
        return teacherCourseList;
    }

    public void addCourse(Course course) {
        if (!this.teacherCourseList.contains(course)) {
            this.teacherCourseList.add(course);
            course.setTeacher(this);
        }
    }

    public List<Exam> getExamList() {
        return teacherExamList;
    }

    public void addExam(Exam exam) {
        if (!this.teacherExamList.contains(exam)) {
            this.teacherExamList.add(exam);
            exam.setAuthor(this);
        }
    }

    public void addQuestion(Question question) {
        if (!this.teacherQuestionList.contains(question)) {
            this.teacherQuestionList.add(question);
            question.setAuthor(this);
        }
    }
}
