package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import org.args.OCSF.AbstractServer;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// server is a singleton
public class EMSserver extends AbstractServer {

    private static EMSserver singleInstanceServer = null;
    private final Session session;
    List<String> loggedInUsers = new ArrayList<>();

    private EMSserver(int port, Session session) {
        super(port);
        this.session = session;
        Thread serverCommands = new Thread(this::serverCommands);
        serverCommands.start();
    }

    public static EMSserver getSingleInstance(int port, Session session) {

        if (singleInstanceServer == null)
            singleInstanceServer = new EMSserver(port, session);

        return singleInstanceServer;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        System.out.println("connected");
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

    private void serverCommands() {

        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.print(">>");
            String input = scanner.nextLine();
            if (input.equals("exit"))
            {
                assert session != null;
                session.close();
                session.getSessionFactory().close();
                try
                {
                    this.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
