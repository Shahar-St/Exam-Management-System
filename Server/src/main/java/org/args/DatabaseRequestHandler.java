package org.args;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
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
            allQuestionsHandler();
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
        String errorMsg;
        HashMap<String, List<String>> map = new HashMap<>();
        SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest) this.request;

        if (client.getInfo("userName") == null)
            errorMsg = "unauthorized access - user isn't logged in";
        else
        {
            User user = getUser((String) client.getInfo("userName"));

            List<Course> courses;
            if (user instanceof Teacher)
                courses = ((Teacher) user).getCoursesList();
            else    // user is dean, get all courses
            {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
                criteriaQuery.from(Course.class);
                Query<Course> query = session.createQuery(criteriaQuery);
                courses = query.getResultList();
            }

            for (Course course : courses)
            {
                if (map.containsKey(course.getSubject().getName()))
                    map.get(course.getSubject().getName()).add(course.getName());
                else
                {
                    List<String> subjectCourses = new ArrayList<>();
                    subjectCourses.add(course.getName());
                    map.put(course.getSubject().getName(), subjectCourses);
                }
            }
            errorMsg = null;
            status = true;
        }

        this.response = new SubjectsAndCoursesResponse(status, request, map, errorMsg);
    }

    private void questionHandler() {
        //Gal
    }

    private void editQuestionHandler() {

        EditQuestionRequest request = (EditQuestionRequest) this.request;
        boolean status = false;
        String errorMsg;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), request.getQuestionID()));
        Query<Question> query = session.createQuery(criteriaQuery);
        Question question = query.getSingleResult();

        if (question == null)
            errorMsg = "Question wasn't found";
        else
        {
            question.setQuestionContent(request.getNewDescription());
            question.setAnswersArray(request.getNewAnswers());
            question.setCorrectAnswer(request.getCorrectAnswer());
            question.setLastModified(LocalDateTime.now());
            session.update(question);
            errorMsg = null;
            status = true;
        }

        this.response = new EditQuestionResponse(status, request, errorMsg);
    }

    private void loginHandler() {
        //Shahar

        boolean status = false;
        String permission = null;
        String errorMsg;

        LoginRequest request = (LoginRequest) this.request;
        User user = getUser(request.getUserName());

        if (user == null)
            errorMsg = "username wasn't found";
        else if (!user.getPassword().equals(request.getPassword()))
            errorMsg = "wrong password";
        else
        {
            permission = user.getClass().getSimpleName().toLowerCase();
            this.client.setInfo("userName", user.getUserName());
            errorMsg = null;
            status = true;
        }

        this.response = new LoginResponse(status, request, permission, errorMsg);
    }

    private void allQuestionsHandler() {

        AllQuestionsRequest request = (AllQuestionsRequest) this.request;
        boolean status = false;
        HashMap<Integer, Pair<LocalDateTime, String>> map = new HashMap<>();
        String errorMsg;

        if (client.getInfo("userName") == null)
            errorMsg = "unauthorized access - user isn't logged in";
        else
        {
            User user = getUser((String) client.getInfo("userName"));

            List<Question> questionList = new ArrayList<>();
            if (user instanceof Teacher)
            {
                Teacher teacher = (Teacher) user;
                for (Course tCourse : teacher.getCoursesList())
                {
                    if (tCourse.getName().equals(request.getCourse()))
                        questionList.addAll(tCourse.getQuestionsList());
                }
            }
            else    // user is dean
            {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
                Root<Question> root = criteriaQuery.from(Question.class);
                criteriaQuery.select(root);
                Query<Question> query = session.createQuery(criteriaQuery);
                questionList.addAll(query.getResultList());
            }

            for (Question question : questionList)
            {
                map.put(Integer.valueOf(question.getId()),
                        new Pair<>(question.getLastModified(), question.getQuestionContent()));
            }
            errorMsg = null;
            status = true;
        }

        this.response = new AllQuestionsResponse(status, request, map, errorMsg);

    }

    private User getUser(String userName) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), userName));
        Query<User> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
