package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.HashMap;
import java.util.List;

/**
 * request: get the all subjects and courses the user have read access to
 * <p>
 * response:
 * Hashmap: key=subject name, value = list of courses
 */
public class SubjectsAndCoursesResponse extends DatabaseResponse {

    private final HashMap<String, List<String>> subjectsAndCourses;

    // successful request
    public SubjectsAndCoursesResponse(boolean status, DatabaseRequest request, HashMap<String, List<String>> subjectsAndCourses) {
        super(status, request, null);
        this.subjectsAndCourses = subjectsAndCourses;
    }

    // unsuccessful request
    public SubjectsAndCoursesResponse(boolean status, DatabaseRequest request, String errorMsg) {
        super(status, request, errorMsg);
        this.subjectsAndCourses = null;
    }

    public HashMap<String, List<String>> getSubjectsAndCourses() {
        return subjectsAndCourses;
    }
}
