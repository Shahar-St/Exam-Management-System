package DatabaseAccess.Requests.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;

public class ReviewAutomatedEvaluationRequest extends DatabaseRequest {
    private final int grade;
    private final String comments;
    private final String gradeChangeReason;
    private final String executedExamId;

    public ReviewAutomatedEvaluationRequest(int grade, String comments, String gradeChangeReason, String executedExamId) {
        this.grade = grade;
        this.comments = comments;
        this.gradeChangeReason = gradeChangeReason;
        this.executedExamId = executedExamId;
    }

    public int getGrade() {
        return grade;
    }



    public String getComments() {
        return comments;
    }



    public String getGradeChangeReason() {
        return gradeChangeReason;
    }


    public String getExecutedExamId() {
        return executedExamId;
    }


}
