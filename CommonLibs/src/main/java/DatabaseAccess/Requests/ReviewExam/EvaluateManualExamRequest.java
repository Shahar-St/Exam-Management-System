package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

import java.io.File;

public class EvaluateManualExamRequest extends DatabaseRequest {
    private final double grade;
    private final String comments;
    private final File wordFile;

    public EvaluateManualExamRequest(double grade, String comments, File wordFile) {
        this.grade = grade;
        this.comments = comments;
        this.wordFile = wordFile;
    }

    public double getGrade() {
        return grade;
    }


    public String getComments() {
        return comments;
    }


    public File getWordFile() {
        return wordFile;
    }


}
