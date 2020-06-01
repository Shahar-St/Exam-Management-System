package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class StudentStatisticsResponse extends DatabaseResponse {
    public StudentStatisticsResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
