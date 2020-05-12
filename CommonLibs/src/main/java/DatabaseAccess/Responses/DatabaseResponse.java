package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.io.Serializable;

/**
 * this class responsible for massage passing from the server to the client
 * <p>
 * status: if the request succeeded or not
 * request: the request this responses to
 */
public abstract class DatabaseResponse implements Serializable {

    private final int status;
    private final DatabaseRequest request;

    //Group c'tors
    public DatabaseResponse(int status, DatabaseRequest request) {
        this.status = status;
        this.request = request;
    }

    //Group getters and setters
    public int getStatus() {
        return status;
    }

    public DatabaseRequest getRequest() {
        return request;
    }
}