package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import org.args.OCSF.AbstractServer;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EMSserver extends AbstractServer {

    private final Session session;
    List<String> loggedInUsers = new ArrayList<>();

    public EMSserver(int port, Session session) {
        super(port);
        this.session = session;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        if (msg instanceof DatabaseRequest)
        {
            DatabaseRequestHandler handler =
                    new DatabaseRequestHandler((DatabaseRequest) msg, client, session, loggedInUsers);
            try
            {
                client.sendToClient(handler.getResponse());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            session.flush();
            session.clear();
        }
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        loggedInUsers.remove((String) client.getInfo("userName"));
    }
}
