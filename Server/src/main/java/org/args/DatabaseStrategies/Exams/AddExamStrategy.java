package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.AddExamRequest;
import DatabaseAccess.Requests.Questions.AddQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import DatabaseAccess.Responses.Exams.AddExamResponse;
import DatabaseAccess.Responses.Questions.AddQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Course;
import org.args.Entities.Exam;
import org.args.Entities.Question;
import org.args.Entities.Teacher;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class AddExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        AddExamRequest addExamRequest = (AddExamRequest) request;

        if (client.getInfo("userName") == null)
            return new AddExamResponse(UNAUTHORIZED, request);

        Teacher teacher = (Teacher) getUser((String) client.getInfo("userName"), session);
        Course course = getTypeById(Course.class, addExamRequest.getCourseID(), session);
        List<Question> questionsList = new ArrayList<>();
        // why?
        for (String question : addExamRequest.getQuestionsIDs())
            questionsList.add(getTypeById(Question.class, question, session));

        Exam exam = new Exam(course, teacher, addExamRequest.getDurationInMinutes(), addExamRequest.getExamTitle(),
                addExamRequest.getStudentNotes(), addExamRequest.getTeacherNotes(), questionsList, addExamRequest.getScoresList());

        session.saveOrUpdate(exam);
        session.flush();
        return new AddExamResponse(SUCCESS, request);

    }
}
