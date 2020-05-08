package org.args.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "teacher_id")
    private Teacher author;

    @ManyToMany(mappedBy = "containedInExams")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    private List<Question> questionsList = new ArrayList<>();

    //private List<Double> questionsScores = new ArrayList<>();

    private int duration; // in minutes
    private String description;
    private String teacherPrivateNotes; // only for the teacher

    //Group c'tors
    public Exam() { }

    public Exam(Course course, Teacher author, int duration, String description, String teacherPrivateNotes) {

        course.addExam(this);
        author.addExam(this);
        this.duration = duration;
        this.description = description;
        this.teacherPrivateNotes = teacherPrivateNotes;

        // handle empty queue
        DecimalFormat decimalFormat = new DecimalFormat("00");
        this.id = course.getSubject().getId() + course.getId() +
                decimalFormat.format(course.getAvailableExamCodes().poll());
    }

    //Group adders and removers
    public void addQuestion(Question question) {

        if (!questionsList.contains(question))
            questionsList.add(question);

        if (!question.getContainedInExams().contains(this))
            question.getContainedInExams().add(this);
    }

    //Group setters and getters
    public Course getCourse() { return course; }
    protected void setCourse(Course course) { this.course = course; }

    public List<Question> getQuestionsList() { return questionsList; }
    public void setQuestionsList(List<Question> questionsList) { this.questionsList = questionsList; }

    public Teacher getAuthor() { return author; }
    protected void setAuthor(Teacher author) { this.author = author; }

    public String getId() { return id; }
    protected void setId(String id) { this.id = id; }

    public int getDuration() { return duration; }
    protected void setDuration(int duration) { this.duration = duration; }

    public String getDescription() { return description; }
    protected void setDescription(String description) { this.description = description; }

    public String getTeacherPrivateNotes() { return teacherPrivateNotes; }
    protected void setTeacherPrivateNotes(String privateNotes) { teacherPrivateNotes = privateNotes; }

//    public List<Double> getQuestionsScores() { return questionsScores; }
//    public void setQuestionsScores(List<Double> questionsScores) { this.questionsScores = questionsScores; }

}
