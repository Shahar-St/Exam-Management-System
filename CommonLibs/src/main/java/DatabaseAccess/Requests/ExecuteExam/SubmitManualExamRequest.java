package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

public class SubmitManualExamRequest extends DatabaseRequest {
    private final String examID;
    private final byte[] examFile;

    public SubmitManualExamRequest(String examID, byte[] examFile) {
        this.examID = examID;
        this.examFile = examFile;
    }

    public String getExamID() {
        return examID;
    }

    public byte[] getExamFile() {
        return examFile;
    }
}
