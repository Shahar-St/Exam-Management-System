package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class ViewReportsOfRequest extends DatabaseRequest {
    private final String sortBy;

    public ViewReportsOfRequest(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

}
