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
            subjectsAndCoursesHandler();
    }

    public DatabaseResponse getResponse() {
        return response;
    }

    private void subjectsAndCoursesHandler() {

        SubjectsAndCoursesRequest request = (SubjectsAndCoursesRequest) this.request;

        if (client.getInfo("userName") == null)
        {
            this.response = new SubjectsAndCoursesResponse(false, request,
                    "unauthorized access - user isn't logged in");
            return;
        }

        HashMap<String, List<String>> map = new HashMap<>();
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
        this.response = new SubjectsAndCoursesResponse(true, request, map);
    }

    private void questionHandler() {

        QuestionRequest request = (QuestionRequest) this.request;

        Question question = getQuestion(String.valueOf(request.getQuestionID()));

        if (question == null)
        {
            this.response = new QuestionResponse(false, request, "Question wasn't found");
            return;
        }

        this.response = new QuestionResponse(true, request, question.getQuestionContent(),
                question.getAnswersArray(), question.getCorrectAnswer(), question.getCourse().getName(),
                question.getAuthor().getFullName(), question.getLastModified());
    }

    private void editQuestionHandler() {

        EditQuestionRequest request = (EditQuestionRequest) this.request;

        Question question = getQuestion(String.valueOf(request.getQuestionID()));

        if (question == null)
        {
            this.response = new EditQuestionResponse(false, request, "Question wasn't found");
            return;
        }
        else if (question.getAuthor() != getUser((String) client.getInfo("userName")))
        {
            this.response = new QuestionResponse(false, request,
                    "You can't edit a question that wasn't written by you");
            return;
        }

        question.setQuestionContent(request.getNewDescription());
        question.setAnswersArray(request.getNewAnswers());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setLastModified(LocalDateTime.now());
        session.update(question);

        this.response = new EditQuestionResponse(true, request);
    }

    private void loginHandler() {

        LoginRequest request = (LoginRequest) this.request;
        User user = getUser(request.getUserName());

        if (user == null)
        {
            this.response = new LoginResponse(false, request, "username wasn't found");
            return;
        }
        else if (!user.getPassword().equals(request.getPassword()))
        {
            this.response = new LoginResponse(false, request, "wrong password");
            return;
        }

        this.client.setInfo("userName", user.getUserName());
        this.response = new LoginResponse(true, request, user.getClass().getSimpleName().toLowerCase());
    }

    private void allQuestionsHandler() {

        AllQuestionsRequest request = (AllQuestionsRequest) this.request;
        HashMap<Integer, Pair<LocalDateTime, String>> map = new HashMap<>();


        if (client.getInfo("userName") == null)
        {
            this.response = new AllQuestionsResponse(false, request,
                    "unauthorized access - user isn't logged in");
            return;
        }

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

        this.response = new AllQuestionsResponse(true, request, map);
    }

    private User getUser(String userName) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), userName));
        Query<User> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    private Question getQuestion(String questionID) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), questionID));
        Query<Question> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
