package org.args;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class DatabaseHandler {

    private Session session;

    // error codes
    private final int SUCCESS = 0;
    private final int UNAUTHORIZED = 1;
    private final int NOT_FOUND = 2;
    private final int NO_ACCESS = 3;
    private final int WRONG_INFO = 4;

    public DatabaseHandler() {
        try
        {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
        }
        catch (Exception exception)
        {
            if (session != null)
                session.getTransaction().rollback();

            System.err.println("An error occurred, changes have been rolled back.");
            exception.printStackTrace();
            assert session != null;
            session.close();
            session.getSessionFactory().close();
        }
    }

    public Session getSession() {
        return session;
    }
    private static SessionFactory getSessionFactory() throws HibernateException {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Dean.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Subject.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Exam.class);
        configuration.addAnnotatedClass(ExecutedExam.class);

        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client,
                                   List<String> loggedInUsers) {
        DatabaseResponse response;

        if (request instanceof LoginRequest)
            response = loginHandler((LoginRequest) request, client, loggedInUsers);
        else if (request instanceof AllQuestionsRequest)
            response = allQuestionsHandler((AllQuestionsRequest) request, client);
        else if (request instanceof EditQuestionRequest)
            response = editQuestionHandler((EditQuestionRequest) request, client);
        else if (request instanceof QuestionRequest)
            response = questionHandler((QuestionRequest) request, client);
        else // request is instanceof SubjectsAndCoursesRequest
            response = subjectsAndCoursesHandler((SubjectsAndCoursesRequest) request, client);

        this.session.clear();
        return response;
    }

    private SubjectsAndCoursesResponse subjectsAndCoursesHandler(SubjectsAndCoursesRequest request,
                                                                 ConnectionToClient client) {

        if (client.getInfo("userName") == null)
            return new SubjectsAndCoursesResponse(UNAUTHORIZED, request);

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
        return new SubjectsAndCoursesResponse(SUCCESS, request, map);
    }

    private QuestionResponse questionHandler(QuestionRequest request, ConnectionToClient client) {

        if (client.getInfo("userName") == null)
            return new QuestionResponse(UNAUTHORIZED, request);

        Question question = getQuestion(request.getQuestionID());

        if (question == null)
            return new QuestionResponse(NOT_FOUND, request);

        List<String> answers = new ArrayList<>(question.getAnswersArray());
        return new QuestionResponse(SUCCESS, request, question.getQuestionContent(),
                answers, question.getCorrectAnswer(), question.getCourse().getName(),
                question.getAuthor().getFullName(), question.getLastModified());
    }

    private EditQuestionResponse editQuestionHandler(EditQuestionRequest request, ConnectionToClient client) {

        if (client.getInfo("userName") == null)
            return new EditQuestionResponse(UNAUTHORIZED, request);

        Question question = getQuestion(request.getQuestionID());

        if (question == null)
            return new EditQuestionResponse(NOT_FOUND, request);


        if (question.getAuthor() != getUser((String) client.getInfo("userName")))
            return new EditQuestionResponse(NO_ACCESS, request);

        question.setQuestionContent(request.getNewDescription());
        question.setAnswersArray(request.getNewAnswers());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setLastModified();
        session.update(question);
        session.flush();

        return new EditQuestionResponse(SUCCESS, request);
    }

    private LoginResponse loginHandler(LoginRequest request, ConnectionToClient client, List<String> loggedInUsers) {

        User user = getUser(request.getUserName());

        if (user == null)
            return new LoginResponse(NOT_FOUND, request);

        if (loggedInUsers.contains(request.getUserName()))
            return new LoginResponse(NO_ACCESS, request);

        if (!user.getPassword().equals(request.getPassword()))
            return new LoginResponse(WRONG_INFO, request);

        client.setInfo("userName", user.getUserName());
        loggedInUsers.add(user.getUserName());
        return new LoginResponse(SUCCESS, user.getClass().getSimpleName().toLowerCase(),
                user.getFullName(), request);
    }

    private AllQuestionsResponse allQuestionsHandler(AllQuestionsRequest request,
                                                     ConnectionToClient client) {

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllQuestionsResponse(UNAUTHORIZED, request);

        User user = getUser((String) client.getInfo("userName"));

        if (user == null)
            return new AllQuestionsResponse(NOT_FOUND, request);


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
            map.put(question.getId(),
                    new Pair<>(question.getLastModified(), question.getQuestionContent()));

        return new AllQuestionsResponse(SUCCESS, request, map);
    }

    private User getUser(String userName) throws NoResultException {

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), userName));
        Query<User> query = session.createQuery(criteriaQuery);
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    private Question getQuestion(String questionID) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), questionID));
        Query<Question> query = session.createQuery(criteriaQuery);
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
