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

    private final boolean status;
    private final String errorMsg;
    private final DatabaseRequest request;

    //Group c'tors
    public DatabaseResponse(boolean status, DatabaseRequest request, String errorMsg) {
        this.status = status;
        this.request = request;
        this.errorMsg = errorMsg;
    }

    //Group getters and setters
    public boolean getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public DatabaseRequest getRequest() {
        return request;
    }
}