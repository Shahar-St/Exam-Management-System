package org.args;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.LoginResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import org.args.Entities.Course;
import org.args.Entities.Student;
import org.args.Entities.Teacher;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseRequestHandler {

    private final DatabaseRequest request;
    private DatabaseResponse response;
    private final Session session;
    private final ConnectionToClient client;

    public DatabaseRequestHandler(DatabaseRequest request, ConnectionToClient client, Session session) {
        this.request = request;
        this.session = session;
        this.client = client;

        if (request instanceof LoginRequest)
            loginHandler();
        else if (request instanceof AllQuestionsRequest)
            allQuestionHandler();
        else if (request instanceof EditQuestionRequest)
            editQuestionHandler();
        else if (request instanceof QuestionRequest)
            questionHandler();
        else if (request instanceof SubjectsAndCoursesRequest)
            subjectAndCoursesHandler();
    }

    public DatabaseResponse getResponse() {
        return response;
    }

    private void subjectAndCoursesHandler() {
        // Shahar

        boolean status = false;
        HashMap<String, List<String>> map = new HashMap<>();
        String errorMsg = null;
        SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest) this.request;

        if (client.getInfo("user") == null)
            errorMsg = "unauthorized access - user isn't logged in";
        else
        {
            Teacher user = (Teacher) client.getInfo("user");

            for (Course course : user.getCoursesList())
            {
                if (map.containsKey(course.getSubject().getName()))
                    map.get(course.getSubject().getName()).add(course.getName());
                else
                {
                    List<String> courses = new ArrayList<>();
                    courses.add(course.getName());
                    map.put(course.getSubject().getName(), courses);
                }
            }
            status = true;
        }

        this.response = new SubjectsAndCoursesResponse(status, request, map, errorMsg);
    }

    private void questionHandler() {
        //Gal
    }

    private void editQuestionHandler() {
    }

    private void loginHandler() {
        //Shahar

        boolean status = false;
        String permission = "";
        String errorMsg = null;

        LoginRequest request = (LoginRequest) this.request;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), request.getUserName()));
        Query<User> query = session.createQuery(criteriaQuery);

        List<User> results = query.getResultList();

        LoginResponse response;
        if (results.isEmpty())
            errorMsg = "username wasn't found";
        else if (!results.get(0).getPassword().equals(request.getPassword()))
            errorMsg = "wrong password";
        else
        {
            permission = results.get(0).getClass().getSimpleName().toLowerCase();
            status = true;
            this.client.setInfo("user", results.get(0));
        }

        this.response = new LoginResponse(status, request, permission, errorMsg);
    }

    private void allQuestionHandler() {
    }
}
