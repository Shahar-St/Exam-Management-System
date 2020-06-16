package org.args.DatabaseStrategies.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.AddQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.AddQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Course;
import org.args.Entities.Question;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - there are already 1000 question for this course
 */

public class AddQuestionStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AddQuestionRequest request1 = (AddQuestionRequest) request;

        if (client.getInfo("userName") == null)
            return new AddQuestionResponse(UNAUTHORIZED, request);

        questionsAndExamsLock.lock();

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        Course course = getTypeById(Course.class, request1.getCourseID(), session);

        if (course.getAvailableQuestionCodes().isEmpty())
        {
            questionsAndExamsLock.unlock();
            return new AddQuestionResponse(ERROR2, request);
        }

        Question question = new Question(request1.getNewDescription(), request1.getNewAnswers(),
                request1.getCorrectAnswer(), course, teacher);

        session.saveOrUpdate(question);
        session.flush();

        questionsAndExamsLock.unlock();

        return new AddQuestionResponse(SUCCESS, request);
    }
}
