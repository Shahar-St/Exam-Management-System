package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExecutedExam;

public class StudentStatisticsResponse extends DatabaseResponse {

    private final LightExecutedExam lightExecutedExam;

    public StudentStatisticsResponse(int status, DatabaseRequest request, LightExecutedExam lightExecutedExam)
    {
        super(status, request);
        this.lightExecutedExam = lightExecutedExam;
    }

    public LightExecutedExam getLightExecutedExam() {
        return lightExecutedExam;
    }
}
