package LightEntities;

import java.io.Serializable;
import java.util.List;

public class LightExam implements Serializable {
    private String id;
    private String authorUserName;
    private List<LightQuestion> lightQuestionList;
    private List<Double> questionsScores;
    private int durationInMinutes;
    private String title;
    private String teacherNotes;
    private String studentNotes;

    public LightExam() {
    }

    public LightExam(String id, String authorUserName, List<LightQuestion> lightQuestionList, List<Double> questionsScores,
                     int durationInMinutes, String title, String teacherPrivateNotes, String studentNotes) {
        this.id = id;
        this.authorUserName = authorUserName;
        this.lightQuestionList = lightQuestionList;
        this.questionsScores = questionsScores;
        this.durationInMinutes = durationInMinutes;
        this.title = title;
        this.teacherNotes = teacherPrivateNotes;
        this.studentNotes = studentNotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public List<LightQuestion> getLightQuestionList() {
        return lightQuestionList;
    }

    public void setLightQuestionList(List<LightQuestion> lightQuestionList) {this.lightQuestionList = lightQuestionList;}

    public List<Double> getQuestionsScores() {
        return questionsScores;
    }

    public void setQuestionsScores(List<Double> questionsScores) {
        this.questionsScores = questionsScores;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String teacherNotes) {this.teacherNotes = teacherNotes; }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }
}
