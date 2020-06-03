package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.AddExamRequest;
import DatabaseAccess.Requests.Exams.AllExamsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.AddExamResponse;
import DatabaseAccess.Responses.Exams.AllExamsResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllExamsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AllExamsRequest allExamsRequest = (AllExamsRequest) request;

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllExamsResponse(UNAUTHORIZED, request);

        User user = getUser((String) client.getInfo("userName"), session);

        if (user == null)
            return new AllQuestionsResponse(NOT_FOUND, request);

        List<Exam> examsList = new ArrayList<>();
        
        if (user instanceof Dean)  // user is dean
            examsList = getAllOfType(session, Exam.class);
        else
        {
            List<Course> coursesList = new ArrayList<>();
            if (user instanceof Teacher)
                coursesList = ((Teacher) user).getCoursesList();
            else // user is a student
                coursesList = ((Student) user).getCoursesList();

            for (Course course : coursesList)
                if (course.getId().equals(allExamsRequest.getCourseID()))
                    examsList.addAll(course.getExamsList());
        }

        for (Exam exam : examsList)
            map.put(exam.getId(),
                    new Pair<>(question.getLastModified(), question.getQuestionContent()));
        


        return new AllExamsResponse(SUCCESS, request, map);
        

    }
}
