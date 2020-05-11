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

    private static final int NUM_OF_SUBJECTS = 3;
    private static final int NUM_OF_TEACHERS = 9;
    private static final int NUM_OF_STUDENTS = 18;
    private static final int NUM_OF_COURSES = 9;
    private static final int NUM_OF_EXAMS = 18;
    private static final int NUM_OF_ANSWERS = 4;

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
        for (int i = 0; i < NUM_OF_SUBJECTS; i++)
        {
            Subject subject = new Subject(i, "subject_" + i);
            session.save(subject);
        }
        session.flush();
        List<Subject> subjects = getAllOfType(Subject.class);

        //creating teachers and connecting with subjects
        for (int i = 0; i < NUM_OF_TEACHERS; i++)
        {
            Teacher teacher = new Teacher(i, "Teacher ", "num" + i, "passTeacher" + i, "teacherUN" + i);
            session.save(teacher);
            subjects.get(i % NUM_OF_SUBJECTS).addTeacher(teacher);
            session.update(teacher);
        }
        session.flush();
        List<Teacher> teachers = getAllOfType(Teacher.class);

        //creating courses
        for (int i = 0; i < NUM_OF_COURSES; i++)
        {
            Course course = new Course(i, "course_" + i, subjects.get(i % NUM_OF_SUBJECTS),
                    teachers.get(i % NUM_OF_TEACHERS));
            session.save(course);
        }
        session.flush();
        List<Course> courses = getAllOfType(Course.class);

        //creating students and connecting with courses
        for (int i = 0; i < NUM_OF_STUDENTS; i++)
        {
            if (i % 2 == 0)
            {
                Student student = new Student(100 + i, "Student ", "num" + i, "passStudent" + i, "studentUN", false);
                session.save(student);
                courses.get(i % NUM_OF_COURSES).addStudent(student);
                session.update(student);
            }
            else
            {
                Student student = new Student(100 + i, "Student ", "num" + i, "passStudent" + i, "studentUN", true);
                session.save(student);
                courses.get(i % NUM_OF_COURSES).addStudent(student);
                session.update(student);
            }
        }
        session.flush();
        List<Student> students = getAllOfType(Student.class);

        //creating exams
        for (int i = 0; i < NUM_OF_EXAMS; i++)
        {
            Exam exam = new Exam(courses.get(i % NUM_OF_COURSES), teachers.get(i % NUM_OF_TEACHERS),
                    120, "good luck", "my secret note");
            session.save(exam);
        }
        session.flush();
        List<Exam> exams = getAllOfType(Exam.class);

        //creating questions and connecting with exams
        String[] ansArr = {"its a", "its b", "its c", "its d"};
        for (int i = 0; i < 36; i++)
        {
            Question question = new Question("question" + i, ansArr, i % NUM_OF_ANSWERS,
                    courses.get(i % NUM_OF_COURSES), teachers.get(i % NUM_OF_TEACHERS));
            session.save(question);
            exams.get(i % NUM_OF_EXAMS).addQuestion(question);
            session.update(question);
        }
        session.flush();

        //creating executedExams
        for (int i = 0; i < 18; i++)
        {
            ExecutedExam executedExam = new ExecutedExam(exams.get(i % NUM_OF_EXAMS),
                    students.get(i % NUM_OF_STUDENTS));
            session.save(executedExam);
        }
        session.flush();
    }

    private static <T> List<T> getAllOfType(Class<T> objectType) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(objectType);
        query.from(objectType);
        return session.createQuery(query).getResultList();
    }
}
