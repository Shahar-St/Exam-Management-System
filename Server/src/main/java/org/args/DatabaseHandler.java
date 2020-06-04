package org.args;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Requests.Questions.DeleteQuestionRequest;
import DatabaseAccess.Responses.*;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.DatabaseStrategies.Exams.*;
import org.args.DatabaseStrategies.LoginStrategy;
import org.args.DatabaseStrategies.Questions.*;
import org.args.DatabaseStrategies.SubjectAndCoursesStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;
import java.util.logging.Level;

public class DatabaseHandler {

    private static DatabaseHandler databaseHandler = null;
    private static Session session;
    private final HashMap<String, DatabaseStrategy> strategies = new HashMap<>() {{
        this.put("LoginRequest", new LoginStrategy());
        this.put("SubjectsAndCoursesRequest", new SubjectAndCoursesStrategy());
        this.put("QuestionRequest", new QuestionStrategy());
        this.put("EditQuestionRequest", new EditQuestionStrategy());
        this.put("AllQuestionsRequest", new AllQuestionsStrategy());
        this.put("DeleteQuestionRequest", new DeleteQuestionStrategy());
        this.put("AddQuestionRequest", new AddQuestionStrategy());
        this.put("AddExamRequest", new AddExamStrategy());
        this.put("AllExamsRequest", new AllExamsStrategy());
        this.put("DeleteExamRequest", new DeleteExamStrategy());
        this.put("EditExamRequest", new EditExamStrategy());
        this.put("ViewExamRequest", new ViewExamStrategy());
    }};

////    public DatabaseResponse test(DeleteQuestionRequest request) {
////        DeleteQuestionStrategy strategy = new DeleteQuestionStrategy();
////        return strategy.test(request, session);
//    }

    public DatabaseHandler() {
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
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Teacher.class)
                .addAnnotatedClass(Dean.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Subject.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Exam.class)
                .addAnnotatedClass(ExecutedExam.class)
                .addAnnotatedClass(ConcreteExam.class);

        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public DatabaseResponse produceResponse(DatabaseRequest request, ConnectionToClient client,
                                            List<String> loggedInUsers) {
        DatabaseResponse response = strategies.get(request.getClass().getSimpleName())
                .handle(request, client, session, loggedInUsers);
        session.clear();
        return response;
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
    private final int NUM_OF_EXAMS = 4;

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

        //creating exams by teachers

        String[] titlesArr = {"functions", "spelling", "circles", "vocabulary"};
        List<Double> questionsScores = Arrays.asList(50.0, 50.0);

        for (int i = 0; i < NUM_OF_EXAMS; i++)
        {
            Teacher teacher = teachers.get(i % NUM_OF_TEACHERS);
            Course course = teacher.getCoursesList().get(i % teacher.getCoursesList().size());
            Exam exam = teacher.createExam(course, 90, titlesArr[i], "good luck!", "my private notes",
                    course.getQuestionsList(), questionsScores);
            session.save(exam);
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
