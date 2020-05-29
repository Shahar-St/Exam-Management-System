package LightEntities;

import java.util.List;

public class LightExecutedExam {
    private LightUser lightUser;
    private String originalExamId;
    private String examCode;
    private int duration;
    private String gradeChangeReasons;
    private String comments;
    private List<Integer> answers;

    public LightExecutedExam() {
    }

    public LightExecutedExam(LightUser lightUser, String originalExamId, String examCode, int duration, String gradeChangeReasons, String comments, List<Integer> answers) {
        this.lightUser = lightUser;
        this.originalExamId = originalExamId;
        this.examCode = examCode;
        this.duration = duration;
        this.gradeChangeReasons = gradeChangeReasons;
        this.comments = comments;
        this.answers = answers;
    }

    public LightUser getLightUser() {
        return lightUser;
    }

    public void setLightUser(LightUser lightUser) {
        this.lightUser = lightUser;
    }

    public String getOriginalExamId() {
        return originalExamId;
    }

    public void setOriginalExamId(String originalExamId) {
        this.originalExamId = originalExamId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGradeChangeReasons() {
        return gradeChangeReasons;
    }

    public void setGradeChangeReasons(String gradeChangeReasons) {
        this.gradeChangeReasons = gradeChangeReasons;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }
}
