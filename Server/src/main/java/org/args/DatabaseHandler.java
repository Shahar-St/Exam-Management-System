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
import java.util.*;
import java.util.logging.Level;

public class DatabaseHandler {

    private static DatabaseHandler databaseHandler = null;
    private Session session;

    // error codes
    private final int SUCCESS = 0;
    private final int UNAUTHORIZED = 1;
    private final int NOT_FOUND = 2;
    private final int NO_ACCESS = 3;
    private final int WRONG_INFO = 4;

    private DatabaseHandler() {
        try
        {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            Scanner scanner = new Scanner(System.in);
            String ans = "";
            System.out.println("Build dummy database? (y/n)");
            System.out.println("//enter y only if the database is empty//");
            while (!ans.equals("y") && !ans.equals("n"))
            {
                ans = scanner.nextLine();
                if (!ans.equals("y") && !ans.equals("n"))
                    System.out.println("wrong input, try again");
            }
            if (ans.equals("y"))
            {
                createDummyEntities();
                session.getTransaction().commit();
                session.clear();
                session.beginTransaction();
            }
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

    public static DatabaseHandler DatabaseHandlerInit() {
        if (databaseHandler == null)
        {
            databaseHandler = new DatabaseHandler();
            return databaseHandler;
        }
        return null;
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

        if (user == null || !user.getUserName().equals(request.getUserName()))
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
    public void close() {
        assert session != null;
        session.close();
        session.getSessionFactory().close();
    }


    //Group dummy DB
    private final int NUM_OF_SUBJECTS = 2;
    private final int NUM_OF_TEACHERS = 4;
    private final int NUM_OF_STUDENTS = 8;
    private final int NUM_OF_COURSES = 4;
    private final int NUM_OF_OPTIONAL_ANSWERS = 4;
    private final int NUM_OF_QUESTIONS = 8;

    private void createDummyEntities() {

        //creating Dean
        Dean dean = new Dean(123456789, "Head", "Teacher", "passDean", "deanUN");
        session.save(dean);
        session.flush();

        //creating subjects
        String[] subjectsNamesArr = {"Math", "English"};
        for (int i = 0; i < NUM_OF_SUBJECTS; i++)
        {
            Subject subject = new Subject(i, subjectsNamesArr[i % subjectsNamesArr.length]);
            session.save(subject);
        }
        session.flush();
        List<Subject> subjects = getAllOfType(session, Subject.class);

        //creating courses and connecting with subjects
        String[] coursesNamesArr = {"Level 1", "Beginners", "Level 2", "Advanced"};
        for (int i = 0; i < NUM_OF_COURSES; i++)
        {
            Course course = new Course(i, coursesNamesArr[i % coursesNamesArr.length], subjects.get(i % NUM_OF_SUBJECTS));
            session.save(course);
        }
        session.flush();
        List<Course> courses = getAllOfType(session, Course.class);

        //creating teachers and connecting with courses and subjects
        String[] teacherFirstNamesArr = {"Ronit", "Miri", "Shir", "Neta"};
        String[] teacherLastNamesArr = {"Cohen", "Haim", "Levi", "Zur"};
        for (int i = 0; i < NUM_OF_TEACHERS; i++)
        {
            Teacher teacher = new Teacher(i, teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherLastNamesArr[i % teacherLastNamesArr.length],
                    teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherFirstNamesArr[i] + "_" + teacherLastNamesArr[i]);
            session.save(teacher);

            courses.get(i % NUM_OF_COURSES).setTeacher(teacher);
            session.update(teacher);
            courses.get(i % NUM_OF_COURSES).getSubject().addTeacher(teacher);
            session.update(teacher);
        }
        session.flush();
        List<Teacher> teachers = getAllOfType(session, Teacher.class);

        //creating students and connecting with courses
        String[] studentFirstNamesArr = {"Yoni", "Guy", "Niv", "Maayan", "Or", "Ariel", "Shoval", "Tal"};
        String[] studentLastNamesArr = {"Cohen", "Haim", "Bar-Dayan", "Shitrit"};
        for (int i = 0; i < NUM_OF_STUDENTS; i++)
        {
            Student student;
            String userName = studentFirstNamesArr[i % studentFirstNamesArr.length] + "_"
                    + studentLastNamesArr[i % studentLastNamesArr.length];

            if (i % 2 == 0)
                student = new Student(NUM_OF_TEACHERS + i, studentFirstNamesArr[i % studentFirstNamesArr.length],
                        studentLastNamesArr[i % studentLastNamesArr.length],
                        studentFirstNamesArr[i % studentFirstNamesArr.length], userName, false);
            else
                student = new Student(NUM_OF_TEACHERS + i, studentFirstNamesArr[i % studentFirstNamesArr.length],
                        studentLastNamesArr[i % studentLastNamesArr.length],
                        studentFirstNamesArr[i % studentFirstNamesArr.length], userName, true);

            session.save(student);
            courses.get((i % NUM_OF_COURSES)).addStudent(student);
        }
        session.flush();

        //creating questions by teachers

        String[] questionsArr = {"1 + 0 = ?", "cat is a/an:", "1 + 4 = ?", "same meaning of happy is:", "0 + 4 = ?",
                "beautiful is a/an:", "1 + 1 = ?", "how to spell many people?"};
        List<String> ansArr1 = Arrays.asList("1", "2", "3", "4");
        List<String> ansArr2 = Arrays.asList("food", "animal", "product", "emotions");
        List<String> ansArr3 = Arrays.asList("0", "3", "5", "-3");
        List<String> ansArr4 = Arrays.asList("sad", "hungry", "afraid", "glad");
        List<String> ansArr5 = Arrays.asList("4", "2", "1", "0");
        List<String> ansArr6 = Arrays.asList("verb", "adjective", "noun", "none of above");
        List<String> ansArr7 = Arrays.asList("1", "3", "2", "4");
        List<String> ansArr8 = Arrays.asList("mans", "man", "mens", "men");
        List<List<String>> answers = new ArrayList<>();
        Collections.addAll(answers, ansArr1, ansArr2, ansArr3, ansArr4, ansArr5, ansArr6, ansArr7, ansArr8);

        for (int i = 0; i < NUM_OF_QUESTIONS; i++)
        {
            Teacher teacher = teachers.get(i % NUM_OF_TEACHERS);
            Question question = teacher.createQuestion(questionsArr[i], answers.get(i), i % NUM_OF_OPTIONAL_ANSWERS,
                    teacher.getCoursesList().get(i % teacher.getCoursesList().size()));
            session.save(question);
            session.update(teacher);
        }
        session.flush();

    }

    private static <T> List<T> getAllOfType(Session session, Class<T> objectType) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(objectType);
        query.from(objectType);
        return session.createQuery(query).getResultList();
    }
}
