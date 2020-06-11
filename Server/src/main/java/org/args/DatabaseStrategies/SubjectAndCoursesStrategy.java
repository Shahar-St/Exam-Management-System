package org.args.DatabaseStrategies;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class SubjectAndCoursesStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        if (client.getInfo("userName") == null)
            return new SubjectsAndCoursesResponse(UNAUTHORIZED, request);

        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        User user = getUser((String) client.getInfo("userName"), session);

        List<Course> courses;

        if (user instanceof Teacher)
            courses = ((Teacher) user).getCoursesList();
        else if (user instanceof Student)
            courses = ((Student) user).getCoursesList();
        else // user instanceof Dean - get all courses
            courses = getAllOfType(session, Course.class);

        for (Course course : courses)
        {
            if (map.containsKey(course.getSubject().getName()))
                map.get(course.getSubject().getName()).put(course.getId(), course.getName());
            else
            {
                HashMap<String, String> subjectCourses = new HashMap<>();
                subjectCourses.put(course.getId(), course.getName());
                map.put(course.getSubject().getName(), subjectCourses);
            }
        }
        return new SubjectsAndCoursesResponse(SUCCESS, request, map);
    }
}
