package org.args;


import DatabaseAccess.Requests.Exams.AddExamRequest;
import DatabaseAccess.Requests.Exams.AllExamsRequest;
import DatabaseAccess.Requests.Exams.DeleteExamRequest;
import DatabaseAccess.Requests.Exams.ViewExamRequest;
import DatabaseAccess.Requests.ExecuteExam.*;
import DatabaseAccess.Requests.LoginRequest;
import DatabaseAccess.Requests.Questions.AllQuestionsRequest;
import DatabaseAccess.Requests.Questions.EditQuestionRequest;
import DatabaseAccess.Requests.Questions.QuestionRequest;
import DatabaseAccess.Requests.ReviewExam.EvaluateExamRequest;
import DatabaseAccess.Requests.ReviewExam.GetExecutedExamRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamsRequest;
import DatabaseAccess.Requests.ReviewExam.UncheckedExecutesOfConcreteRequest;
import DatabaseAccess.Requests.Statistics.TeacherStatisticsRequest;
import DatabaseAccess.Requests.SubjectsAndCoursesRequest;
import DatabaseAccess.Responses.Exams.AddExamResponse;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import DatabaseAccess.Responses.Exams.DeleteExamResponse;
import DatabaseAccess.Responses.Exams.ViewExamResponse;
import DatabaseAccess.Responses.ExecuteExam.*;
import DatabaseAccess.Responses.LoginResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import DatabaseAccess.Responses.Questions.EditQuestionResponse;
import DatabaseAccess.Responses.Questions.QuestionResponse;
import DatabaseAccess.Responses.ReviewExam.EvaluateExamResponse;
import DatabaseAccess.Responses.ReviewExam.GetExecutedExamResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamsResponse;
import DatabaseAccess.Responses.ReviewExam.UncheckedExecutesOfConcreteResponse;
import DatabaseAccess.Responses.Statistics.TeacherStatisticsResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import LightEntities.LightExam;
import LightEntities.LightExecutedExam;
import LightEntities.LightQuestion;
import Notifiers.RaiseHandNotifier;
import Notifiers.TimeExtensionRequestNotifier;
import Util.Pair;
import org.args.server.AbstractServer;
import org.args.server.ConnectionToClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * Hello world!
 */
public class ServerApp extends AbstractServer {
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public ServerApp(int port) {
        super(port);
    }

    public static void main(String[] args) {
        try {
            ServerApp serverApp = new ServerApp(3000);
            serverApp.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int counter = 0;

    byte[] data;

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("message received:");
        System.out.println(msg);
        List<LightQuestion> questionList = new ArrayList<>();
        questionList.add(new LightQuestion("1 + 0 = ? ", Arrays.asList("1", "2", "3", "4"), 0, "FuckThisShit!", "malki", LocalDateTime.now(), "5"));
        questionList.add(new LightQuestion("0 + 4 = ? ", Arrays.asList("11", "12", "13", "14"), 0, "FuckThisShit!", "malki", LocalDateTime.now(), "6"));
        questionList.add(new LightQuestion("cat is a/an:", Arrays.asList("shit", "shitty", "a", "an"), 0, "FuckThisShit!", "malki", LocalDateTime.now(), "7"));
        questionList.add(new LightQuestion("same meaning of happy is:", Arrays.asList("shimon", "shimon", "shimon", "shimon"), 0, "FuckThisShit!", "malki", LocalDateTime.now(), "8"));
        LightExam exam = new LightExam("1111", "malki", questionList, Arrays.asList(25.0, 25.0, 25.0, 25.0), 90, "exampleTest", "teacher sucks", "student rocks");

        if (msg instanceof LoginRequest) {
            LoginRequest request = (LoginRequest) msg;
            try {
            if(request.getUserName().equals("KK"))
                client.sendToClient(new LoginResponse(0, "teacher", "yoni", request));
            else if(request.getUserName().equals("dean"))
                client.sendToClient(new LoginResponse(0, "dean", "dean", request));
            else
                client.sendToClient(new LoginResponse(0,"teacher","malki",request));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof QuestionRequest) {
            QuestionRequest request = (QuestionRequest) msg;
            try {
                client.sendToClient(new QuestionResponse(0, request, "exampleQuestion", Arrays.asList("ans1", "ans2", "ans3", "ans4"), 0, "shimon", LocalDateTime.now()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof EditQuestionRequest) {
            EditQuestionRequest request = (EditQuestionRequest) msg;
            try {
                client.sendToClient(new EditQuestionResponse(0, request));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof SubjectsAndCoursesRequest) {
            SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest) msg;
            HashMap<String, HashMap<String, String>> response = new HashMap<>();
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("001", "DS");
            map1.put("002", "OS");
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("001", "Linear");
            map2.put("002", "AMIFUCKEDYOU");
            response.put("CS", map1);
            response.put("Math", map2);
            try {
                client.sendToClient(new SubjectsAndCoursesResponse(0, request, response));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof AllQuestionsRequest) {
            AllQuestionsRequest request = (AllQuestionsRequest) msg;
            HashMap<String, Pair<LocalDateTime, String>> response = new HashMap<>();
            response.put("1", new Pair<>(LocalDateTime.now(), "Shela " + counter++));
            response.put("2", new Pair<>(LocalDateTime.now(), "Shela " + counter++));
            response.put("3", new Pair<>(LocalDateTime.now(), "Shela " + counter++));
            response.put("4", new Pair<>(LocalDateTime.now(), "Shela " + counter++));

            try {
                client.sendToClient(new AllQuestionsResponse(0, request, response));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof ViewExamRequest) {
            ViewExamRequest request = (ViewExamRequest) msg;
            ViewExamResponse response = new ViewExamResponse(0, request, exam);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof AllExamsRequest) {
            AllExamsRequest request = (AllExamsRequest) msg;
            HashMap<String, Pair<LocalDateTime, String>> examList = new HashMap<>();
            examList.put("000001", new Pair<>(LocalDateTime.now(), "Algebra"));
            examList.put("000002", new Pair<>(LocalDateTime.now(), "English"));
            examList.put("000003", new Pair<>(LocalDateTime.now(), "Sex Education"));
            examList.put("000004", new Pair<>(LocalDateTime.now(), "Pooping Lesson"));
            AllExamsResponse response = new AllExamsResponse(0, request, examList);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof DeleteExamRequest) {
            try {
                client.sendToClient(new DeleteExamResponse(0, (DeleteExamRequest) msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof AddExamRequest) {
            AddExamRequest request = (AddExamRequest) msg;
            try {
                client.sendToClient(new AddExamResponse(0, request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof TeacherStatisticsRequest) {
            TeacherStatisticsRequest request = (TeacherStatisticsRequest) msg;
            HashMap<String, Double> map = new HashMap<>();
            map.put("123456", 100.0);
            map.put("122222", 99.0);
            try {
                client.sendToClient(new TeacherStatisticsResponse(0, request, map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof ExecuteExamRequest) {
            ExecuteExamRequest request = (ExecuteExamRequest) msg;
            ExecuteExamResponse response = new ExecuteExamResponse(0, request);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof TakeExamRequest) {
            TakeExamRequest request = (TakeExamRequest) msg;
            TakeExamResponse response = new TakeExamResponse(0, request, exam);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (msg instanceof TimeExtensionRequest) {
            TimeExtensionRequest request = (TimeExtensionRequest) msg;
            TimeExtensionResponse response = new TimeExtensionResponse(0,request);
            TimeExtensionRequestNotifier notifier = new TimeExtensionRequestNotifier("11", "22", "malki", "kaki", "1234", 10, "kaka");
            sendToAllClients(notifier);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof RaiseHandRequest) {
            RaiseHandRequest request = (RaiseHandRequest) msg;
            RaiseHandResponse response = new RaiseHandResponse(0,request,"Yoni");
            RaiseHandNotifier notifier = new RaiseHandNotifier("Yoni");
            sendToAllClients(notifier);
        } else if (msg instanceof PendingExamsRequest) {
            PendingExamsRequest request = (PendingExamsRequest) msg;
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1111, "DS");
            map.put(2222, "OS");
            map.put(3333, "SE");
            try {
                client.sendToClient(new PendingExamsResponse(0, request, map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof UncheckedExecutesOfConcreteRequest) {
            UncheckedExecutesOfConcreteRequest request = (UncheckedExecutesOfConcreteRequest) msg;
            HashMap<String, Boolean> map = new HashMap<>();
            map.put("12346789", true);
            map.put("987654321", false);
            try {
                client.sendToClient(new UncheckedExecutesOfConcreteResponse(0, request, map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msg instanceof GetExecutedExamRequest) {
            GetExecutedExamRequest request = (GetExecutedExamRequest) msg;
            LightExecutedExam lightExecutedExam = new LightExecutedExam("DS", "shimon", "1234", "12346789", exam.getLightQuestionList(), exam.getQuestionsScores(), exam.getDurationInMinutes(), true);
            lightExecutedExam.setManualExam(data);
            lightExecutedExam.setAnswersByStudent(Arrays.asList(0,0,0,0));
            GetExecutedExamResponse response = new GetExecutedExamResponse(0, request, lightExecutedExam);
            try {
                client.sendToClient(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(msg instanceof SubmitManualExamRequest){
            SubmitManualExamRequest request = (SubmitManualExamRequest)msg;
            data = request.getExamFile();
            try {
                client.sendToClient(new SubmitExamResponse(0,request));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (msg instanceof EvaluateExamRequest){
            EvaluateExamRequest request = (EvaluateExamRequest)msg;
            EvaluateExamResponse response = new EvaluateExamResponse(0,request);
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
