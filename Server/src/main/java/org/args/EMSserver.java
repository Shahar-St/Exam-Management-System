package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import org.args.OCSF.AbstractServer;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;


public class EMSserver extends AbstractServer {

    private final Session session;

    public EMSserver(int port, Session session) {
        super(port);
        this.session = session;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        if (msg instanceof DatabaseRequest)
        {
            DatabaseRequestHandler handler = new DatabaseRequestHandler((DatabaseRequest) msg, client, session);
            try
            {
                client.sendToClient(handler.getResponse());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        //need to remove from logged in users
    }
}
