package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

// student side
public class TakeExamRequest extends DatabaseRequest {

    private final int socialId;
    private final String examCode;
    private final boolean computerized;

    public TakeExamRequest(int socialId, String examCode, boolean computerized) {
        this.socialId = socialId;
        this.examCode = examCode;
        this.computerized = computerized;
    }

    public int getSocialId() {
        return socialId;
    }

    public String getExamCode() {
        return examCode;
    }

    public boolean isComputerized() {
        return computerized;
    }
}
