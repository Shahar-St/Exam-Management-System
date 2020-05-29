package DatabaseAccess.Requests;

public class ViewReportsOfRequest extends DatabaseRequest {
    private String sortBy;

    public ViewReportsOfRequest(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
