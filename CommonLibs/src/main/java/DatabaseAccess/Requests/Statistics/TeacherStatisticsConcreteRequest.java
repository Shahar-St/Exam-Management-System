package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherStatisticsConcreteRequest extends DatabaseRequest {

    private final String concreteId;

    public TeacherStatisticsConcreteRequest(String concreteId) {
        this.concreteId = concreteId;
    }

    public String getConcreteId() {
        return concreteId;
    }
}
