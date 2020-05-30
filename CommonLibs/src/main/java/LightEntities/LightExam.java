package LightEntities;

import java.io.Serializable;
import java.util.List;

public class LightExam implements Serializable {
    private String id;
    private String author;
    private List<LightQuestion> lightQuestionList;
    private List<Double> questionsScores;
    private int durationInMinutes;
    private String description;
    private String teacherPrivateNotes;

    public LightExam() {
    }

    public LightExam(String id, String author, List<LightQuestion> lightQuestionList, List<Double> questionsScores, int durationInMinutes, String description, String teacherPrivateNotes) {
        this.id = id;
        this.author = author;
        this.lightQuestionList = lightQuestionList;
        this.questionsScores = questionsScores;
        this.durationInMinutes = durationInMinutes;
        this.description = description;
        this.teacherPrivateNotes = teacherPrivateNotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<LightQuestion> getLightQuestionList() {
        return lightQuestionList;
    }

    public void setLightQuestionList(List<LightQuestion> lightQuestionList) {
        this.lightQuestionList = lightQuestionList;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherPrivateNotes() {
        return teacherPrivateNotes;
    }

    public void setTeacherPrivateNotes(String teacherPrivateNotes) {
        this.teacherPrivateNotes = teacherPrivateNotes;
    }
}
