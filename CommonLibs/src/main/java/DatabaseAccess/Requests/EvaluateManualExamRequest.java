package DatabaseAccess.Requests;

import java.io.File;

public class EvaluateManualExamRequest extends DatabaseRequest{
    private int grade;
    private String comments;
    private File wordFile;

    public EvaluateManualExamRequest(int grade, String comments, File wordFile) {
        this.grade = grade;
        this.comments = comments;
        this.wordFile = wordFile;
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

    public File getWordFile() {
        return wordFile;
    }

    public void setWordFile(File wordFile) {
        this.wordFile = wordFile;
    }
}
