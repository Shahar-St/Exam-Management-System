package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * student begins to execute an exam
 */
public class TakeExamRequest extends DatabaseRequest {

    private final String socialId;
    private final String examCode;
    private final boolean computerized;

    public TakeExamRequest(String socialId, String examCode, boolean computerized) {
        this.socialId = socialId;
        this.examCode = examCode;
        this.computerized = computerized;
    }

    public String getSocialId() {
        return socialId;
    }

    public String getExamCode() {
        return examCode;
    }

    public boolean isComputerized() {
        return computerized;
    }
}
