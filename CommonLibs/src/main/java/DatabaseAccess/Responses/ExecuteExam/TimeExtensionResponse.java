package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

//this is what the Teacher receives from the server after the Dean confirmed/rejected her time extension request.

public class TimeExtensionResponse extends DatabaseResponse {
    private final String deanResponse;
    private final int authorizedTimeExtension;
    private final boolean isAccepted;

    public TimeExtensionResponse(int status, DatabaseRequest request, Boolean isAccepted, String deanResponse, int authorizedTimeExtension) {
        super(status, request);
        this.deanResponse = deanResponse;
        this.authorizedTimeExtension = authorizedTimeExtension;
        this.isAccepted = isAccepted;
    }

    public String getDeanResponse() {
        return deanResponse;
    }

    public int getAuthorizedTimeExtension() {
        return authorizedTimeExtension;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
