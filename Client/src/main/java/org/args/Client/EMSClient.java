package org.args.Client;

import org.args.GUI.ClientApp;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class EMSClient extends AbstractClient {

    private final ClientApp app;

    public EMSClient(String host, int port, ClientApp clientApp) {
        super(host, port);
        this.app = clientApp;
    }

    @Override
    public void sendToServer(Object msg) {
        // check if the client is not connected to the server then connect
        // good for initial connection and for disconnections
        try {
            if (!super.isConnected()) {

                super.openConnection();

            }
            super.sendToServer(msg);
        } catch (IOException e) {
            connectionException(e);
        }
        System.out.println("Message Has Been Sent To The Server");
        System.out.println(msg.toString());
    }

    public void logOut() {
        if (super.isConnected()) {

            try {
                super.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        System.out.println("Disconnected From Server");
    }

    @Override
    protected void connectionException(Exception exception) {
        super.connectionException(exception);
        System.out.println("Connection Exception : " + exception.toString());
        app.errorAlert("Connection To Server Failed.");
    }

    @Override
    protected void connectionEstablished() {
        super.connectionEstablished();
        System.out.println("Established Connection To Server ");
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        EventBus.getDefault().post(msg);
    }


}
