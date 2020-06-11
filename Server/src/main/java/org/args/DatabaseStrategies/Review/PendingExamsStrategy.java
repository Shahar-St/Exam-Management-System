package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamsResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.Exam;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
//TODO
public class PendingExamsStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {
        PendingExamsRequest request1 = (PendingExamsRequest)request;
        if (client.getInfo("userName") == null)
            return new PendingExamsResponse(UNAUTHORIZED,request1,null);
        Teacher teacher = getTypeById(Teacher.class,getUser((String) client.getInfo("userName"),session).getSocialId(),session);
        HashMap<Integer, String> map = new HashMap<>();
        for(ConcreteExam concreteExam:teacher.getConcreteExamsList()){
            Exam currentExam = concreteExam.getExam();
            map.put(concreteExam.getId(),currentExam.getTitle());
        }
        if(map.isEmpty())
            return new PendingExamsResponse(ERROR2,request1,null);
        return new PendingExamsResponse(0,request1,map);
    }
}
