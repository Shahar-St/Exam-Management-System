package LightEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LightExecutedExam implements Serializable {

    private final String title;
    private final String testerUserName;
    private final String executedID;
    private final String studentID;
    private final List<LightQuestion> lightQuestionList;
    private final List<Double> questionsScores;
    private List<Integer> answersByStudent = new ArrayList<>();
    private String reasonsForChangeGrade;
    private String commentsAfterCheck;
    private double grade = 0;
    private final int duration; // exam duration in minutes
    private final boolean isComputerized;
    private boolean checked = false;
    private byte[] manualExam = null;

    public LightExecutedExam(String title, String testerUserName, String executedID, String studentID,
                             List<LightQuestion> lightQuestionList, List<Double> questionsScores,
                             List<Integer> answersByStudent, int duration, boolean isComputerized, double grade,
                             String reasonsForChangeGrade, String commentsAfterCheck) {
        this.title = title;
        this.testerUserName = testerUserName;
        this.executedID = executedID;
        this.studentID = studentID;
        this.lightQuestionList = lightQuestionList;
        this.questionsScores = questionsScores;
        this.duration = duration;
        this.isComputerized = isComputerized;
        this.answersByStudent = answersByStudent;
        this.grade = grade;
        this.reasonsForChangeGrade = reasonsForChangeGrade;
        this.commentsAfterCheck = commentsAfterCheck;
    }

    public String getTitle() {
        return title;
    }
    public String getTesterUserName() {
        return testerUserName;
    }
    public String getExecutedID() {
        return executedID;
    }
    public String getStudentID() {
        return studentID;
    }
    public List<LightQuestion> getLightQuestionList() {
        return lightQuestionList;
    }
    public List<Double> getQuestionsScores() {
        return questionsScores;
    }
    public List<Integer> getAnswersByStudent() {
        return answersByStudent;
    }
    public String getReasonsForChangeGrade() {
        return reasonsForChangeGrade;
    }
    public void setReasonsForChangeGrade(String reasonsForChangeGrade) {
        this.reasonsForChangeGrade = reasonsForChangeGrade;
    }
    public String getCommentsAfterCheck() {
        return commentsAfterCheck;
    }
    public void setCommentsAfterCheck(String commentsAfterCheck) {
        this.commentsAfterCheck = commentsAfterCheck;
    }
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
    public int getDuration() {
        return duration;
    }
    public boolean isComputerized() {
        return isComputerized;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public byte[] getManualExam() {
        return manualExam;
    }
    public void setManualExam(byte[] manualExam) {
        this.manualExam = manualExam;
    }

    public void setAnswersByStudent(List<Integer> answersByStudent) {
        this.answersByStudent = answersByStudent;
    }
}
