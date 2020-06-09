package DatabaseAccess.Requests.ExecuteExam;

// Dean handles the ConfirmTimeExtensionResponse from the server, then sends this request.

public class ConfirmTimeExtensionRequest {

    private final String deanResponse;
    private final int authorizedTimeExtension;
    private final boolean isAccepted;

    public ConfirmTimeExtensionRequest(Boolean isAccepted, String deanResponse, int authorizedTimeExtension) {
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
