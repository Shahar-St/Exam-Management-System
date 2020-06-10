package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

// student side
public class TakeExamRequest extends DatabaseRequest {
    private final int socialId;
    private final String examCode;

    public TakeExamRequest(int socialId, String examCode) {
        this.socialId = socialId;
        this.examCode = examCode;
    }

    public int getSocialId() {
        return socialId;
    }


    public String getExamCode() {
        return examCode;
    }

}
