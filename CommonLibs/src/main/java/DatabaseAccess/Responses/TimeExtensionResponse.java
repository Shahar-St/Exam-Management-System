package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

public class TimeExtensionResponse extends DatabaseResponse {
    public TimeExtensionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
