package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.AddQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.AddQuestionResponse;
import DatabaseAccess.Responses.Questions.EditQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Course;
import org.args.Entities.Question;
import org.args.Entities.Teacher;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

public class AddQuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AddQuestionRequest request1 = (AddQuestionRequest) request;

        if (client.getInfo("userName") == null)
            return new AddQuestionResponse(UNAUTHORIZED, request);

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        Course course = getTypeById(Course.class, request1.getCourseID(), session);

        Question question = new Question(request1.getNewDescription(), request1.getNewAnswers(),
                request1.getCorrectAnswer(), course, teacher);

        session.saveOrUpdate(question);
        session.flush();
        return new AddQuestionResponse(SUCCESS, request);
    }
}
