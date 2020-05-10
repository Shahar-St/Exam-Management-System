package org.args;


import org.args.server.AbstractServer;
import org.args.server.ConnectionToClient;
import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ServerApp extends AbstractServer
{
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public ServerApp(int port) {
        super(port);
    }

    public static void main(String[] args )
    {
        try {
            ServerApp serverApp = new ServerApp(1337);
            serverApp.listen();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("message received");
        System.out.println(msg);
        System.out.println(msg.toString());

    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.println("Client connected");
        super.clientConnected(client);
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        System.out.println("Client disconnected");
        super.clientDisconnected(client);
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        super.clientException(client, exception);
        System.out.println(exception.toString());
    }

    @Override
    protected void listeningException(Throwable exception) {
        super.listeningException(exception);
        System.out.println(exception.toString());
    }
}
