package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.List;

public class SubmitExamRequest extends DatabaseRequest {

    private final String examID;
    private final List<Integer> answersList;

    public SubmitExamRequest(String examID, List<Integer> answersList) {
        this.examID = examID;
        this.answersList = answersList;
    }

    public String getExamID() {
        return examID;
    }

    public List<Integer> getAnswersList() {
        return answersList;
    }
}
