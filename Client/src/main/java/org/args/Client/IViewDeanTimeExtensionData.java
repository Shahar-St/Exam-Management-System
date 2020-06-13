package org.args.Client;

import javafx.collections.ObservableList;

public interface IViewDeanTimeExtensionData {
    ObservableList<String> getObservableTimeExtensionRequestsList();

    void removeRequest(String selectedItem);

    void rejectExtension(String reason);

    void acceptExtension(String extension);
}
