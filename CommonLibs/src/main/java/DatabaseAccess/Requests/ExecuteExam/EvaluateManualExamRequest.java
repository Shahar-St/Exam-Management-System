package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

import java.io.File;

public class EvaluateManualExamRequest extends DatabaseRequest {
    private final int grade;
    private final String comments;
    private final File wordFile;

    public EvaluateManualExamRequest(int grade, String comments, File wordFile) {
        this.grade = grade;
        this.comments = comments;
        this.wordFile = wordFile;
    }

    public int getGrade() {
        return grade;
    }


    public String getComments() {
        return comments;
    }


    public File getWordFile() {
        return wordFile;
    }


}
