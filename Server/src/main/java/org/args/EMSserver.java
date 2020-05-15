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
    private final DatabaseHandler databaseHandler;
    List<String> loggedInUsers = new ArrayList<>();

    private EMSserver(int port, DatabaseHandler databaseHandler) {
        super(port);
        this.databaseHandler = databaseHandler;
        this.session = databaseHandler.getSession();

        Thread serverCommands = new Thread(this::serverCommands);
        serverCommands.start();
    }

    public static EMSserver EMSserverInit(int port, DatabaseHandler databaseHandler) {

        if (singleInstanceServer == null)
        {
            singleInstanceServer = new EMSserver(port, databaseHandler);
            return singleInstanceServer;
        }
        return null;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        System.out.println("Interrupted\nreceived message from client " + client.getInetAddress()
                + "::" + msg.getClass().getSimpleName() + "\n>> ");

        if (msg instanceof DatabaseRequest)
        {
            try
            {
                client.sendToClient(databaseHandler.handle((DatabaseRequest) msg, client, loggedInUsers));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.print("Interrupted\nClient connected: " + client.getInetAddress() + "\n>> ");
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        System.out.println("Interrupted\nClient " + client.getInetAddress() + " Disconnected." + "\n>> ");
        loggedInUsers.remove((String) client.getInfo("userName"));
    }

    private void serverCommands() {

        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.print(">> ");
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
