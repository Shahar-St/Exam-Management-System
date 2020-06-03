package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class TimeExtensionResponse extends DatabaseResponse {
    private final String deanResponse;
    private final int authorizedTimeExtension;

    public TimeExtensionResponse(int status, DatabaseRequest request, String deanResponse, int authorizedTimeExtension) {
        super(status, request);
        this.deanResponse = deanResponse;
        this.authorizedTimeExtension = authorizedTimeExtension;
    }

    public String getDeanResponse() {
        return deanResponse;
    }

    public int getAuthorizedTimeExtension() {
        return authorizedTimeExtension;
    }
}
