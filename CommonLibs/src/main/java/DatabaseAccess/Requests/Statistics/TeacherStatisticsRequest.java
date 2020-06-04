package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherStatisticsRequest extends DatabaseRequest {
    private final String executedExamID;

    public TeacherStatisticsRequest(String executedExamID) {
        this.executedExamID = executedExamID;
    }

    public String getExecutedExamID() {
        return executedExamID;
    }
}
