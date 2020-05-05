package Entities;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Check {

    private static final int NUM_OF_PERSONS = 5;
    private static final int NUM_OF_CARS = 5;
    private static final int NUM_OF_GARAGES = 2;
    private static final int NUM_OF_IMAGES = 5;
    private static Session session;

    public static void main(String[] args) {
        try
        {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            generateAll();
          //  connectEntities();
            session.getTransaction().commit();
            session.clear();
        }
        catch (Exception exception)
        {
            if (session != null)
                session.getTransaction().rollback();

            System.err.println("An error occurred, changes have been rolled back.");
            exception.printStackTrace();
        } finally
        {
            assert session != null;
            session.close();
            session.getSessionFactory().close();
        }
    }

    private static SessionFactory getSessionFactory() throws HibernateException {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Exam.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(ExecutedExam.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Subject.class);
        configuration.addAnnotatedClass(Teacher.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateAll() {
            String [] arr = {"a","b","c","d"};
            Teacher teacher = new Teacher(111111111, "gal","miro", "pass","galmiro");
            Subject subject = new Subject("21");
            Course course = new Course("12", new Subject(), teacher);
            Question question = new Question("hii", "make it", arr, 2, course, teacher);
            Student student = new Student(2222222,"gal","miro","pass","galm", false);
            Exam exam = new Exam(subject,course,teacher,90,"do it","my secret description");
            ExecutedExam examExecuted = new ExecutedExam(exam,student);

            student.addCourse(course);
            teacher.addCourse(course);
            teacher.addExam(exam);
            teacher.addQuestion(question);
            teacher.addSubject(subject);
            exam.addExamQuestion(question,50);

            session.save(question);
            session.flush();
    }

    private static <T> List<T> getAllOfType(Class<T> objectType) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(objectType);
        query.from(objectType);
        return session.createQuery(query).getResultList();
    }

    private static <T> void printAllOfType(Class<T> objectType) {
        List<T> tList = getAllOfType(objectType);
        for (T object : tList)
            System.out.println(object);
    }


}