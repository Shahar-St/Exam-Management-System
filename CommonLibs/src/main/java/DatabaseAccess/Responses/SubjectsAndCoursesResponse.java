package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.util.HashMap;
import java.util.List;

/**
 * request: get the all subjects and courses the user have read access to
 * <p>
 * response:
 * Hashmap: key=subject name, value = hashmap<course ID, course name>
 * <p>
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */
public class SubjectsAndCoursesResponse extends DatabaseResponse {

    private final HashMap<String, HashMap<String, String>> subjectsAndCourses;

    // successful request
    public SubjectsAndCoursesResponse(int status, DatabaseRequest request, HashMap<String, HashMap<String, String>> subjectsAndCourses) {
        super(status, request);
        this.subjectsAndCourses = subjectsAndCourses;
    }

    // unsuccessful request
    public SubjectsAndCoursesResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.subjectsAndCourses = null;
    }

    public HashMap<String, HashMap<String, String>> getSubjectsAndCourses() {
        return subjectsAndCourses;
    }
}