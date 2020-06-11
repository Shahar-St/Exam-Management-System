package org.args;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.DeleteQuestionRequest;
import org.args.Entities.ConcreteExam;
import org.args.OCSF.AbstractServer;
import org.args.OCSF.ConnectionToClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// server is a singleton
public class EMSserver extends AbstractServer {

    private static EMSserver singleInstanceServer = null;
    private final DatabaseHandler databaseHandler;
    List<String> loggedInUsers = new ArrayList<>();

    private EMSserver(int port, DatabaseHandler databaseHandler) {
        super(port);
        this.databaseHandler = databaseHandler;
    }

    public static EMSserver EMSserverInit(DatabaseHandler databaseHandler) {

        if (singleInstanceServer == null)
        {
            System.out.print("Enter port number: ");
            Scanner scanner = new Scanner(System.in);
            int port = scanner.nextInt();

            singleInstanceServer = new EMSserver(port, databaseHandler);
            return singleInstanceServer;
        }
        return null;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        System.out.print("Interrupted\nreceived message from client " + client.getInetAddress()
                + "::" + msg.getClass().getSimpleName() + "\n>> ");

        if (msg instanceof DatabaseRequest)
        {
            try
            {
                client.sendToClient(databaseHandler.produceResponse((DatabaseRequest) msg,
                        client, loggedInUsers));
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
        System.out.print("Interrupted\nClient disconnected." + "\n>> ");
        loggedInUsers.remove((String) client.getInfo("userName"));
    }

    @Override
    protected void serverStarted() {
        try
        {
            System.out.println("server is online, server IP: " + InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        Thread serverCommands = new Thread(this::serverCommands);
        serverCommands.start();
    }

    private void serverCommands() {

        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equals("exit"))
            {
                try
                {
                    this.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                databaseHandler.close();
                return;
            }
        }
    }
}
