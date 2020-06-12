package org.args.DatabaseStrategies.Review;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ReviewExam.PendingExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ReviewExam.PendingExamsResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.ConcreteExam;
import org.args.Entities.Exam;
import org.args.Entities.ExecutedExam;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
//TODO
public class PendingExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {
        
        PendingExamsRequest pendingExamsRequest = (PendingExamsRequest)request;

        if (client.getInfo("userName") == null)
            return new PendingExamsResponse(ERROR2, pendingExamsRequest, null);

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);

        HashMap<Integer, String> map = new HashMap<>();
        boolean needToCheck;
        for(ConcreteExam concreteExam : teacher.getConcreteExamsList())
        {
            List<ExecutedExam> executedExamList = concreteExam.getExecutedExamsList();
            needToCheck = true;
            for(int i=0; i< executedExamList.size() && needToCheck; i++)
            {
                if(!executedExamList.get(i).isChecked())
                {
                    map.put(concreteExam.getId(), concreteExam.getExam().getTitle());
                    needToCheck = false;
                }
            }
        }

        if(map.isEmpty())
            return new PendingExamsResponse(ERROR3, pendingExamsRequest, null);

        return new PendingExamsResponse(SUCCESS, pendingExamsRequest, map);

    }
}
