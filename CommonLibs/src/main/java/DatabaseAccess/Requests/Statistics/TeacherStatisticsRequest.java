package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherStatisticsRequest extends DatabaseRequest {
    private final String executedExamGradeList;

    public TeacherStatisticsRequest(String executedExamGradeList) {
        this.executedExamGradeList = executedExamGradeList;
    }

    public String getExecutedExamGradeList() {
        return executedExamGradeList;
    }
}
