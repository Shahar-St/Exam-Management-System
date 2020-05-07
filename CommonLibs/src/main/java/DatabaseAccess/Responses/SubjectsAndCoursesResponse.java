package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.HashMap;
import java.util.List;

/**
 * request: get the all subjects and courses the user have read access to
 * <p>
 * response:
 * Hashmap: key=subject name, value = list of courses
 *
 */
public class SubjectsAndCoursesResponse extends DatabaseResponse {

    private final HashMap<String, List<String>> subjectsandcourses;

    public SubjectsAndCoursesResponse(boolean status, DatabaseRequest request, HashMap<String,List<String>> subjectsandcourses) {
        super(status, request);
        this.subjectsandcourses = subjectsandcourses;
    }

    public HashMap<String, List<String>> getSubjectsandcourses() {
        return subjectsandcourses;
    }
}
