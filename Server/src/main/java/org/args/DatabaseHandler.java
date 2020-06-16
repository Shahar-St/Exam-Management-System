package org.args;

import DatabaseAccess.Requests.*;
import DatabaseAccess.Responses.*;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.DatabaseStrategies.Exams.*;
import org.args.DatabaseStrategies.ExecuteExam.*;
import org.args.DatabaseStrategies.LoginStrategy;
import org.args.DatabaseStrategies.Questions.*;
import org.args.DatabaseStrategies.Review.EvaluateExamStrategy;
import org.args.DatabaseStrategies.Review.GetExecutedExamStrategy;
import org.args.DatabaseStrategies.Review.PendingExamsStrategy;
import org.args.DatabaseStrategies.Review.UncheckedExecutesOfConcreteStrategy;
import org.args.DatabaseStrategies.Statistics.GetAllPastExamsStrategy;
import org.args.DatabaseStrategies.Statistics.TeacherGetAllPastExamsStrategy;
import org.args.DatabaseStrategies.Statistics.TeacherStatisticsStrategy;
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

@SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
public class DatabaseHandler {

    private static DatabaseHandler databaseHandler = null;
    private static Session session;
    final Map<Integer, ExamManager> examManagers = new HashMap<>();    //key = concreteExam ID
    int countOfOperations = 0;

    // how often a commit we'll happen
    private final int REFRESHING_FREQUENCY = 3;

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
        this.put("ExecuteExamRequest", new ExecuteExamStrategy());
        this.put("TakeExamRequest", new TakeExamStrategy());
        this.put("SubmitExamRequest", new SubmitExamStrategy());
        this.put("SubmitManualExamRequest", new SubmitManualExamStrategy());
        this.put("TimeExtensionRequest", new TimeExtensionStrategy());
        this.put("ConfirmTimeExtensionRequest", new ConfirmTimeExtensionStrategy());
        this.put("EvaluateExamRequest", new EvaluateExamStrategy());
        this.put("GetExecutedExamRequest", new GetExecutedExamStrategy());
        this.put("PendingExamsRequest", new PendingExamsStrategy());
        this.put("UncheckedExecutesOfConcreteRequest", new UncheckedExecutesOfConcreteStrategy());
        this.put("GetAllPastExamsRequest", new GetAllPastExamsStrategy());
        this.put("TeacherEndExamRequest", new TeacherEndExamStrategy());
        this.put("RaiseHandRequest", new RaiseHandStrategy());
        this.put("TeacherGetAllPastExamsRequest", new TeacherGetAllPastExamsStrategy());
        this.put("TeacherStatisticsRequest", new TeacherStatisticsStrategy());
    }};

    private DatabaseHandler() {
        try
        {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            System.out.println("Creating dummy database");
            createDummyEntities();
            session.getTransaction().commit();
            session.clear();
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
        DatabaseStrategy strategy = strategies.get(request.getClass().getSimpleName());
        DatabaseResponse response = strategy.handle(request, client, session, loggedInUsers);

        if (strategy instanceof IExamInProgress && response.getStatus() == 0)
            ((IExamInProgress) strategy).handle(request, response, examManagers, client, session);

        session.clear();
        countOfOperations++;

        // once in REFRESHING_FREQUENCY we'll do a commit
        if (countOfOperations % REFRESHING_FREQUENCY == 0)
        {
            session.getTransaction().commit();
            session.clear();
            session.beginTransaction();
        }

        return response;
    }

    public void close() {
        assert session != null;
        session.close();
        session.getSessionFactory().close();
    }

    //Group dummy DB
    private final int NUM_OF_SUBJECTS = 4;
    private final int NUM_OF_TEACHERS = 2;
    private final int NUM_OF_STUDENTS = 16;
    private final int NUM_OF_COURSES = 8;
    private final int NUM_OF_OPTIONAL_ANSWERS = 4;
    private final int NUM_OF_QUESTIONS = 16;
    private final int NUM_OF_EXAMS = 8;

    private void createDummyEntities() {

        //creating Dean
        Dean dean = new Dean(123456789, "Liel", "Fridman", "Liel", "Liel");
        session.save(dean);
        session.flush();

        //creating subjects
        String[] subjectsNamesArr = {"Math", "English", "History", "Hebrew"};
        for (int i = 0; i < NUM_OF_SUBJECTS; i++)
        {
            Subject subject = new Subject(i, subjectsNamesArr[i % subjectsNamesArr.length]);
            session.save(subject);
        }
        session.flush();
        List<Subject> subjects = getAllOfType(session, Subject.class);

        //creating courses and connecting with subjects
        String[] coursesNamesArr = {"4 points", "Beginners", "Israel", "level 1", "5 points", "Advanced", "Holocaust",
                "level 2"};
        for (int i = 0; i < NUM_OF_COURSES; i++)
        {
            Course course = new Course(i, coursesNamesArr[i % coursesNamesArr.length], subjects.get(i % NUM_OF_SUBJECTS));
            session.save(course);
        }
        session.flush();
        List<Course> courses = getAllOfType(session, Course.class);

        //creating teachers and connecting with courses and subjects
        String[] teacherFirstNamesArr = {"1", "Miri", "Shir", "Neta", "Ronit", "Shiri", "Yuval", "shahar"};
        String[] teacherLastNamesArr = {"1", "Haim", "Levi", "Zur", "Hen", "Levi", "Lev", "Oren"};
        for (int i = 0; i < NUM_OF_TEACHERS; i++)
        {
            Teacher teacher = new Teacher(i, teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherLastNamesArr[i % teacherLastNamesArr.length],
                    teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherFirstNamesArr[i] + "_" + teacherLastNamesArr[i]);
            session.save(teacher);
        }
        session.flush();
        List<Teacher> teachers = getAllOfType(session, Teacher.class);

        for (int i = 0; i < NUM_OF_COURSES; i++)
        {
            Teacher teacher = teachers.get(i % NUM_OF_TEACHERS);
            courses.get(i % NUM_OF_COURSES).setTeacher(teacher);
            session.update(teacher);
            courses.get(i % NUM_OF_COURSES).getSubject().addTeacher(teacher);
            session.update(teacher);
        }


        //creating students and connecting with courses
        String[] studentFirstNamesArr = {"Yoni", "Guy", "Niv", "Maayan", "Or", "Ariel", "Shoval", "Tal",
                "Tal", "Shoval", "Ariel", "Or", "Maayan", "Niv", "Guy", "Yoni"};
        String[] studentLastNamesArr = {"Cohen", "Haim", "Bar-Dayan", "Shitrit", "Lev", "Yaron", "Raz", "Ezer"};
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

        String[] questionsArr = {"1 + 0 = ?", "capital city of Israel:", "1 + 4 = ?", "how many jewish people died?:",
                "0 + 4 = ?", "the biggest city in Israel is:", "1 + 1 = ?", "the leader was:",
                "cat is a/an:", "after 'a':", "same meaning of happy is:", "after 'c':",
                "beautiful is a/an:", "capital of 'b':", "how to spell many people?", "capital of 'd':"};
        List<String> ansArr1 = Arrays.asList("1", "2", "3", "4");
        List<String> ansArr2 = Arrays.asList("Haifa", "Jerusalem", "Tel Aviv", "Dimona");
        List<String> ansArr3 = Arrays.asList("0", "3", "5", "-3");
        List<String> ansArr4 = Arrays.asList("3M", "4M", "5M", "6M");
        List<String> ansArr5 = Arrays.asList("4", "2", "1", "0");
        List<String> ansArr6 = Arrays.asList("Haifa", "Jerusalem", "Tel Aviv", "Dimona");
        List<String> ansArr7 = Arrays.asList("1", "3", "2", "4");
        List<String> ansArr8 = Arrays.asList("Gal", "Shahar", "Adar", "Hitler");
        List<String> ansArr9 = Arrays.asList("animal", "food", "product", "emotions");
        List<String> ansArr10 = Arrays.asList("a", "b", "c", "d");
        List<String> ansArr11 = Arrays.asList("sad", "hungry", "glad", "afraid");
        List<String> ansArr12 = Arrays.asList("a", "b", "c", "d");
        List<String> ansArr13 = Arrays.asList("adjective", "verb", "noun", "none of above");
        List<String> ansArr14 = Arrays.asList("A", "B", "C", "D");
        List<String> ansArr15 = Arrays.asList("mans", "man", "men", "mens");
        List<String> ansArr16 = Arrays.asList("A", "B", "C", "D");
        List<List<String>> answers = new ArrayList<>();
        Collections.addAll(answers, ansArr1, ansArr2, ansArr3, ansArr4, ansArr5, ansArr6, ansArr7, ansArr8,
                ansArr9, ansArr10, ansArr11, ansArr12, ansArr13, ansArr14, ansArr15, ansArr16);

        int k = 0;
        for (int j = 0; j < NUM_OF_TEACHERS; j++)
        {
            Teacher teacher = teachers.get(j % NUM_OF_TEACHERS);
            for (int i = 0; i < NUM_OF_QUESTIONS / NUM_OF_TEACHERS; i++)
            {
                Question question = teacher.createQuestion(questionsArr[k], answers.get(k),
                        i % NUM_OF_OPTIONAL_ANSWERS,
                        teacher.getCoursesList().get(i % teacher.getCoursesList().size()));
                session.save(question);
                session.update(teacher);
                k++;
            }
        }
        session.flush();

        //creating exams by teachers
        String[] titlesArr = {"functions", "Jerusalem", "circles", "Germany", "letters", "nikud", "spelling", "vocabulary"};
        List<Double> questionsScores = Arrays.asList(50.0, 50.0);
        List<Integer> answersByStudent = Arrays.asList(2, 3, 2, 3);
        k = 0;
        for (int j = 0; j < NUM_OF_TEACHERS; j++)
        {
            Teacher teacher = teachers.get(j % NUM_OF_TEACHERS);
            for (int i = 0; i < NUM_OF_EXAMS / NUM_OF_TEACHERS; i++)
            {
                Course course = teacher.getCoursesList().get(i % teacher.getCoursesList().size());
                Exam exam = teacher.createExam(course, 2, titlesArr[k], "good luck!", "my private notes",
                        course.getQuestionsList(), questionsScores);
                session.save(exam);
                session.update(teacher);
                ConcreteExam concreteExam = new ConcreteExam(exam, teacher, "1111");
                session.save(concreteExam);
                ExecutedExam executedExam1 = new ExecutedExam(concreteExam, exam.getCourse().getStudentsList().get(0),
                        "not so good...", answersByStudent, "");
                session.save(executedExam1);
                executedExam1.setSubmitted(true);
                executedExam1.setComputerized(true);
                if (i % 2 == 0)
                    executedExam1.setChecked(true);
                executedExam1.setGrade(50);
                ExecutedExam executedExam2 = new ExecutedExam(concreteExam, exam.getCourse().getStudentsList().get(1),
                        "very good!", answersByStudent, "");
                session.save(executedExam2);
                executedExam2.setSubmitted(true);
                executedExam2.setComputerized(true);
                if (i % 2 == 0)
                    executedExam2.setChecked(true);
                executedExam2.setGrade(100);
                k++;
            }
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
