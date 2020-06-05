package org.args.Client;

import org.args.GUI.ClientApp;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.logging.Logger;

public class EMSClient extends AbstractClient {

    private static final Logger LOGGER =
            Logger.getLogger(EMSClient.class.getName());

    private ClientApp app;

    public EMSClient(String host, int port, ClientApp clientApp) throws IOException {
        super(host, port);
        this.app = clientApp;
    }

    public ClientApp getApp() {
        return app;
    }

    @Override
    public void sendToServer(Object msg) throws IOException {
        // check if the client is not connected to the server then connect
        // good for initial connection and for disconnections
        if (!super.isConnected())
        {
            super.openConnection();
        }
        super.sendToServer(msg);
        System.out.println("Message Has Been Sent To The Server");
        System.out.println(msg.toString());
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
        exception.printStackTrace();
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
