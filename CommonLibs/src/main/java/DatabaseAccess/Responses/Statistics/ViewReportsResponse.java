package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.util.List;

public class ViewReportsResponse extends DatabaseResponse {
    private final List<String> reports;

    public ViewReportsResponse(int status, DatabaseRequest request, List<String> reports) {
        super(status, request);
        this.reports = reports;
    }

    public List<String> getReports() {
        return reports;
    }
}
