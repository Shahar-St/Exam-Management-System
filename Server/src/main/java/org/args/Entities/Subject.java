package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
public class Subject {

    @Id
    @Column(nullable = false, unique = true)
    //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;

    @ManyToMany(mappedBy = "subjectsList")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<Teacher> teachersList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Course> coursesList = new ArrayList<>();

    // to remove
    private static Queue<Integer> availableSubjectId = null;

    //Group c'tors
    public Subject() { }

    public Subject(String name) {
        this.name = name;
        if (availableSubjectId == null)
        {
            availableSubjectId = new LinkedList<>();
            for (int i = 0; i < 100; i++)
                availableSubjectId.add(i);
        }
        DecimalFormat nf = new DecimalFormat("00");
        this.id = nf.format(availableSubjectId.poll());
    }

    //Group adders and removers
    public void addTeacher(Teacher teacher) {
       if(!teachersList.contains(teacher))
           teachersList.add(teacher);

        if (!teacher.getSubjectsList().contains(this))
            teacher.getSubjectsList().add(this);
    }

    public void addCourse(Course course) {
        if (!coursesList.contains(course))
        {
            coursesList.add(course);
            course.setSubject(this);
        }
    }

    //Group setters and getters
    public static Queue<Integer> getAvailableSubjectId() { return availableSubjectId; }

    public String getId() {
        return id;
    }
    protected void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachersList() {
        return teachersList;
    }
    public void setTeachersList(List<Teacher> teachersList) {
        this.teachersList = teachersList;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }
    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }
}