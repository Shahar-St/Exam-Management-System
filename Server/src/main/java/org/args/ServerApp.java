package org.args;

import org.args.Entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ServerApp {

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {

        // java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
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

    private static final int NUM_OF_SUBJECTS = 2;
    private static final int NUM_OF_TEACHERS = 4;
    private static final int NUM_OF_STUDENTS = 8;
    private static final int NUM_OF_COURSES = 4;
    private static final int NUM_OF_EXAMS = 4;
    private static final int  NUM_OF_OPTIONAL_ANSWERS = 4;
    private static final int NUM_OF_QUESTIONS = 8;
    private static final int NUM_OF_EXECUTED_EXAMS = 8;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port number:");
        int port = scanner.nextInt();
        try
        {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            createDummyEntities();

            EMSserver server = new EMSserver(port, session);

            session.getTransaction().commit();
            server.listen();

            session.clear();
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


    private static void createDummyEntities() {

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
        List<Subject> subjects = getAllOfType(Subject.class);

        //creating teachers and connecting with subjects
        String[] teacherFirstNamesArr = {"Ronit", "Miri", "Shir", "Neta"};
        String[] teacherLastNamesArr = {"Cohen", "Haim", "Levi", "Zur"};
        for (int i = 0; i < NUM_OF_TEACHERS; i++)
        {
            Teacher teacher = new Teacher(i, teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherLastNamesArr[i % teacherLastNamesArr.length],
                    teacherFirstNamesArr[i % teacherFirstNamesArr.length],
                    teacherFirstNamesArr[i] + "_" + teacherLastNamesArr[i]);
            session.save(teacher);

            subjects.get(i % NUM_OF_SUBJECTS).addTeacher(teacher);
            session.update(teacher);
        }
        session.flush();
        List<Teacher> teachers = getAllOfType(Teacher.class);

        //creating courses and connecting with teachers
        String[] coursesNamesArr = {"Level 1", "Beginners","Level 2", "Advanced"};
        for (int i = 0; i < NUM_OF_COURSES; i++)
        {
            Course course = new Course(i, coursesNamesArr[i % coursesNamesArr.length],
                    subjects.get(i % NUM_OF_SUBJECTS), teachers.get(i % NUM_OF_TEACHERS));
            session.save(course);
        }
        session.flush();
        List<Course> courses = getAllOfType(Course.class);

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
        List<Student> students = getAllOfType(Student.class);

        String[] questionsArr = {"1 + 0 = ?", "cat is a/an:", "1 + 4 = ?",  "same meaning of happy is:", "0 + 4 = ?",
                "beautiful is a/an:",  "1 + 1 = ?", "how to spell many people?"};
        List<String> ansArr1 = Arrays.asList("1", "2", "3", "4");
        List<String> ansArr2 = Arrays.asList("food", "animal", "product", "emotions");
        List<String> ansArr3 = Arrays.asList("0", "3", "5", "-3");
        List<String> ansArr4 = Arrays.asList("sad", "hungry", "afraid", "glad");
        List<String> ansArr5 = Arrays.asList("4", "2", "1", "0");
        List<String> ansArr6 = Arrays.asList("verb", "adjective", "noun", "none of above");
        List<String> ansArr7 =  Arrays.asList("1", "3", "2", "4");
        List<String> ansArr8 = Arrays.asList("mans", "man","mens", "men");
        List[] answers = new List[]{ansArr1, ansArr2, ansArr3, ansArr4, ansArr5, ansArr6, ansArr7, ansArr8};

        for (int i = 0; i < NUM_OF_QUESTIONS; i++)
        {
            Teacher teacher = teachers.get(i % NUM_OF_TEACHERS);
            teacher.createQuestion(questionsArr[i], answers[i], i % NUM_OF_OPTIONAL_ANSWERS,
                                   teacher.getCoursesList().get(i %  teacher.getCoursesList().size()));

//            Question question = new Question(questionsArr[i], answers[i], i % NUM_OF_OPTIONAL_ANSWERS,
//                    courses.get(i % NUM_OF_COURSES), teachers.get(i % NUM_OF_TEACHERS));
            session.update(teacher);
        }

//        List<Question> questions = getAllOfType(Question.class);
//        //students connecting with courses
//        courses.get(0).addStudent(students.get(0));
//        courses.get(0).addStudent(students.get(4));
//        courses.get(1).addStudent(students.get(2));
//        courses.get(1).addStudent(students.get(6));
//        courses.get(2).addStudent(students.get(1));
//        courses.get(2).addStudent(students.get(5));
//        courses.get(3).addStudent(students.get(3));
//        courses.get(3).addStudent(students.get(7));
//        session.flush();

//        //creating exams
//        for (int i = 0; i < NUM_OF_EXAMS; i++)
//        {
//            Exam exam = new Exam(courses.get(i % NUM_OF_COURSES), teachers.get(i % NUM_OF_TEACHERS),
//                    120, "good luck", "my secret note");
//            session.save(exam);
//        }
//        session.flush();
//        List<Exam> exams = getAllOfType(Exam.class);

        //creating questions

//        //connecting questions with exams
//        exams.get(0).addQuestion(questions.get(0));
//        exams.get(0).addQuestion(questions.get(1));
//        exams.get(1).addQuestion(questions.get(4));
//        exams.get(1).addQuestion(questions.get(5));
//        exams.get(2).addQuestion(questions.get(2));
//        exams.get(2).addQuestion(questions.get(3));
//        exams.get(3).addQuestion(questions.get(6));
//        exams.get(3).addQuestion(questions.get(7));
//        session.flush();

//        //creating executedExams
//        for (int i = 0; i < NUM_OF_EXECUTED_EXAMS; i++)
//        {
//            ExecutedExam executedExam = new ExecutedExam(exams.get(i % NUM_OF_EXAMS),
//                    students.get(i % NUM_OF_STUDENTS));
//            session.save(executedExam);
//        }
//        session.flush();
    }

    private static <T> List<T> getAllOfType(Class<T> objectType) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(objectType);
        query.from(objectType);
        return session.createQuery(query).getResultList();
    }
}
