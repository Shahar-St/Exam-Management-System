package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

import java.io.File;

public class SubmitManualExamRequest extends DatabaseRequest {
    private final String examID;
    private final File examFile;

    public SubmitManualExamRequest(String examID, File examFile) {
        this.examID = examID;
        this.examFile = examFile;
    }

    public String getExamID() {
        return examID;
    }

    public File getExamFile() {
        return examFile;
    }
}
