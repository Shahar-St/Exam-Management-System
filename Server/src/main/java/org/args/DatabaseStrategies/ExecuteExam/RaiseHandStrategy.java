package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.RaiseHandRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Requests.ExecuteExam.TakeExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.RaiseHandResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitExamResponse;
import DatabaseAccess.Responses.ExecuteExam.TakeExamResponse;
import LightEntities.LightExam;
import Notifiers.RaiseHandNotifier;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Student;
import org.args.Entities.Teacher;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

//TODO
public class RaiseHandStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        RaiseHandRequest raiseHandRequest = (RaiseHandRequest) request;
        if (client.getInfo("userName") == null)
            return new RaiseHandResponse(UNAUTHORIZED, request,"");
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        return new RaiseHandResponse(SUCCESS, request,student.getFullName());
    }

    public void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                       ConnectionToClient client, Session session) {
        RaiseHandRequest raiseHandRequest = (RaiseHandRequest) request;
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam exam = getTypeById(ExecutedExam.class,String.valueOf(student.getCurrentlyExecutedID()),session);
        ConnectionToClient teacher = examManagers.get(exam.getConcreteExam().getId()).getTeacher();
        ExamManager.notifyTeacherAboutRaisedHand(new RaiseHandNotifier(student.getFullName()),teacher);
        //FIXME This doesn't work. Please check it out!
    }
}
