package org.args;

import java.io.IOException;

public class ServerApp {


    public static void main(String[] args) {

        DatabaseHandler databaseHandler = DatabaseHandler.DatabaseHandlerInit();
        EMSserver server = EMSserver.EMSserverInit(databaseHandler);
        try
        {
            assert server != null;
            server.listen();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
