package DatabaseAccess.Requests;

import LightEntities.LightExecutedExam;

public class ConfirmAutomatedEvaluationRequest  extends DatabaseRequest {
    private int grade;
    private String comments;
    private String gradeChangeReason;
    private LightExecutedExam lightExecutedExam;

    public ConfirmAutomatedEvaluationRequest(int grade, String comments, String gradeChangeReason, LightExecutedExam lightExecutedExam) {
        this.grade = grade;
        this.comments = comments;
        this.gradeChangeReason = gradeChangeReason;
        this.lightExecutedExam = lightExecutedExam;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGradeChangeReason() {
        return gradeChangeReason;
    }

    public void setGradeChangeReason(String gradeChangeReason) {
        this.gradeChangeReason = gradeChangeReason;
    }

    public LightExecutedExam getLightExecutedExam() {
        return lightExecutedExam;
    }

    public void setLightExecutedExam(LightExecutedExam lightExecutedExam) {
        this.lightExecutedExam = lightExecutedExam;
    }
}
