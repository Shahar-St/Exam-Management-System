package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;


/**
 * being initiated by the teacher
 */
public class ExecuteExamRequest extends DatabaseRequest {

    private final String examID;
    private final String examCode;

    public ExecuteExamRequest(String examID, String examCode) {
        this.examID = examID;
        this.examCode = examCode;
    }

    public String getExamID() {
        return examID;
    }

    public String getExamCode() {
        return examCode;
    }
}
