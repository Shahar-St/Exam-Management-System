package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherStatisticsRequest extends DatabaseRequest {
    private final String concreteExamID;

    public TeacherStatisticsRequest(String concreteExamID) {
        this.concreteExamID = concreteExamID;
    }

    public String getConcreteExamID() {
        return concreteExamID;
    }
}
