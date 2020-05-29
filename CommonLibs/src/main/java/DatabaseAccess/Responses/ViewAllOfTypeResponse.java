package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.List;

public class ViewAllOfTypeResponse<T> extends DatabaseResponse{
    private List<T> data;

    public ViewAllOfTypeResponse(int status, DatabaseRequest request, List<T> data) {
        super(status, request);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
