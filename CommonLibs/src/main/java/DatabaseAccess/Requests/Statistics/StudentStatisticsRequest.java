package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class StudentStatisticsRequest extends DatabaseRequest {

        private final String executedExamId;


    public StudentStatisticsRequest(String executedExamId) {

        this.executedExamId = executedExamId;
    }

    public String getExecutedExamId() {
        return executedExamId;
    }
}
