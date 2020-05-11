package org.args;


import org.args.server.AbstractServer;
import org.args.server.ConnectionToClient;
import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        System.out.println("message received:");
        System.out.println(msg);

        if(msg instanceof LoginRequest){
            LoginRequest request = (LoginRequest)msg;
            try {
                client.sendToClient(new LoginResponse(true, request, "TeacherPermission", ""));
            }catch (IOException e){
                e.printStackTrace();
            }

        }else if(msg instanceof QuestionRequest){
            QuestionRequest request = (QuestionRequest)msg;
            try {
                client.sendToClient(new QuestionResponse(true,request,"how much ?",new String[]{"1", "2", "3", "4"},0,"Eating shit 101","malkishoa", LocalDateTime.now(),""));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof EditQuestionRequest){
            EditQuestionRequest request = (EditQuestionRequest)msg;
            try {
                client.sendToClient(new EditQuestionResponse(true,request,""));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof SubjectsAndCoursesRequest){
            SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest)msg;
            HashMap<String, List<String>> response = new HashMap<>();
            response.put("CS", Arrays.asList("DS", "OS"));
            response.put("Math",Arrays.asList("Linear","AMIFUCKEDYOU"));
            try {
                client.sendToClient(new SubjectsAndCoursesResponse(true,request,response,""));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof AllQuestionsRequest){
            AllQuestionsRequest request = (AllQuestionsRequest)msg;
            HashMap<Integer,Pair<LocalDateTime, String>> response = new HashMap<Integer,Pair<LocalDateTime,String>>();
            response.put(1,new Pair<>(LocalDateTime.now(),"Shela 1"));
            response.put(2,new Pair<>(LocalDateTime.now(),"Shela 2"));
            response.put(3,new Pair<>(LocalDateTime.now(),"Shela 3"));
            response.put(4,new Pair<>(LocalDateTime.now(),"Shela 4"));

            try {
                client.sendToClient(new AllQuestionsResponse(true,request,response,""));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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
