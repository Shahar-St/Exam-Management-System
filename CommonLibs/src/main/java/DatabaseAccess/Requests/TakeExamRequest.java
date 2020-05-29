package DatabaseAccess.Requests;

public class TakeExamRequest extends DatabaseRequest {
    private int socialId;
    private String examCode;

    public TakeExamRequest(int socialId, String examCode) {
        this.socialId = socialId;
        this.examCode = examCode;
    }

    public int getSocialId() {
        return socialId;
    }

    public void setSocialId(int socialId) {
        this.socialId = socialId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }
}
