package Notifiers;

import java.io.Serializable;

public class ConfirmTimeExtensionNotifier implements Serializable {

    private String deanResponse;
    private final int authorizedTimeExtension;
    private final boolean isAccepted;

    public ConfirmTimeExtensionNotifier(String deanResponse, int authorizedTimeExtension, boolean isAccepted) {
        this.deanResponse = deanResponse;
        this.authorizedTimeExtension = authorizedTimeExtension;
        this.isAccepted = isAccepted;
    }

    public void setDeanResponse(String deanResponse) {
        this.deanResponse = deanResponse;
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
