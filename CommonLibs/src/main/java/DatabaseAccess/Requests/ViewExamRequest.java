package DatabaseAccess.Requests;

public class ViewExamRequest extends DatabaseRequest{
    private String examId;

    public ViewExamRequest(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }
}
