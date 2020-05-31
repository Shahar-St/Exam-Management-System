package org.args;


import LightEntities.LightExam;
import LightEntities.LightQuestion;
import LightEntities.LightUser;
import org.args.server.AbstractServer;
import org.args.server.ConnectionToClient;
import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            ServerApp serverApp = new ServerApp(3000);
            serverApp.listen();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    int counter = 0;

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("message received:");
        System.out.println(msg);

        if(msg instanceof LoginRequest){
            LoginRequest request = (LoginRequest)msg;
            try {
                client.sendToClient(new LoginResponse(0,request,new LightUser("1111","Shimon Korman","Teacher")));
            }catch (IOException e){
                e.printStackTrace();
            }

        }else if(msg instanceof QuestionRequest){
            QuestionRequest request = (QuestionRequest)msg;
            try {
                client.sendToClient(new QuestionResponse(0,request,new LightQuestion("shela1",Arrays.asList("ans1","ans2","ans3","ans4"),0,"malki kaki",LocalDateTime.now())));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof EditQuestionRequest){
            EditQuestionRequest request = (EditQuestionRequest)msg;
            try {
                client.sendToClient(new EditQuestionResponse(0,request));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof SubjectsAndCoursesRequest){
            SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest)msg;
            HashMap<String, List<String>> response = new HashMap<>();
            response.put("CS", Arrays.asList("DS", "OS"));
            response.put("Math",Arrays.asList("Linear","AMIFUCKEDYOU"));
            try {
                client.sendToClient(new SubjectsAndCoursesResponse(0,request,response));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof AllQuestionsRequest){
            AllQuestionsRequest request = (AllQuestionsRequest)msg;
            HashMap<String,Pair<LocalDateTime, String>> response = new HashMap<String,Pair<LocalDateTime,String>>();
            response.put("1",new Pair<>(LocalDateTime.now(),"Shela " + counter++));
            response.put("2",new Pair<>(LocalDateTime.now(),"Shela " + counter++));
            response.put("3",new Pair<>(LocalDateTime.now(),"Shela " + counter++));
            response.put("4",new Pair<>(LocalDateTime.now(),"Shela " + counter++));

            try {
                client.sendToClient(new AllQuestionsResponse(0,request,response));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(msg instanceof ViewExamRequest){
            List<LightQuestion> questionList = new ArrayList<>();
            questionList.add(new LightQuestion("1 + 0 = ? ",Arrays.asList("1","2","3","4"),0,"malki",LocalDateTime.now()));
            questionList.add(new LightQuestion("0 + 4 = ? ",Arrays.asList("11","12","13","14"),0,"malki",LocalDateTime.now()));
            questionList.add(new LightQuestion("cat is a/an:",Arrays.asList("shit","shitty","a","an"),0,"malki",LocalDateTime.now()));
            questionList.add(new LightQuestion("same meaning of happy is:",Arrays.asList("shimon","shimon","shimon","shimon"),0,"malki",LocalDateTime.now()));
            LightExam exam = new LightExam("1111","malki",questionList,Arrays.asList(25.0,25.0,25.0,25.0),90,"exampleTest","malkishoa notes");
            ViewExamRequest request = (ViewExamRequest)msg;
            ViewExamResponse response = new ViewExamResponse(0,request,exam);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(msg instanceof AllExamsRequest){
            AllExamsRequest request = (AllExamsRequest)msg;
            HashMap<String, Pair<LocalDateTime, String>> examList = new HashMap<>();
            examList.put("exam1",new Pair<>(LocalDateTime.now(),"Content1"));
            examList.put("exam2",new Pair<>(LocalDateTime.now(),"Content2"));
            examList.put("exam3",new Pair<>(LocalDateTime.now(),"Content3"));
            examList.put("exam4",new Pair<>(LocalDateTime.now(),"Content4"));
            AllExamsResponse response = new AllExamsResponse(0,request,examList);
            try {
                client.sendToClient(response);
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
