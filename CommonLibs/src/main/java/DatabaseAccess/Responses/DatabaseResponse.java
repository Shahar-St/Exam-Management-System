package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * this class responsible for massage passing from the server to the client
 *
 * status: if the request succeeded or not
 * request: the request this responses to
 */
public abstract class DatabaseResponse {

    private final boolean status;
    private final DatabaseRequest request;

    //Group c'tors
    public DatabaseResponse(boolean status, DatabaseRequest request) {
        this.status = status;
        this.request = request;
    }

    //Group getters
    public boolean isStatus() {
        return status;
    }
    public DatabaseRequest getRequest() {
        return request;
    }
}
