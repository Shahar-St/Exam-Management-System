package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.List;

/**
 * student submits an exam
 */
public class SubmitExamRequest extends DatabaseRequest {

    private final String examID;
    private final List<Integer> answersList;
    private final boolean finishedOnTime;

    public SubmitExamRequest(String examID, List<Integer> answersList, boolean finishedOnTime) {
        this.examID = examID;
        this.answersList = answersList;
        this.finishedOnTime = finishedOnTime;
    }

    public String getExamID() {
        return examID;
    }

    public List<Integer> getAnswersList() {
        return answersList;
    }

    public boolean isFinishedOnTime() {
        return finishedOnTime;
    }
}
