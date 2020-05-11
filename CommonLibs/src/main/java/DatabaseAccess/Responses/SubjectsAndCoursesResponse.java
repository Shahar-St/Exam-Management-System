package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.HashMap;
import java.util.List;

/**
 * request: get the all subjects and courses the user have read access to
 * <p>
 * response:
 * Hashmap: key=subject name, value = list of courses
 * <p>
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */
public class SubjectsAndCoursesResponse extends DatabaseResponse {

    private final HashMap<String, List<String>> subjectsAndCourses;

    // successful request
    public SubjectsAndCoursesResponse(int status, DatabaseRequest request, HashMap<String, List<String>> subjectsAndCourses) {
        super(status, request);
        this.subjectsAndCourses = subjectsAndCourses;
    }

    // unsuccessful request
    public SubjectsAndCoursesResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.subjectsAndCourses = null;
    }

    public HashMap<String, List<String>> getSubjectsAndCourses() {
        return subjectsAndCourses;
    }
}