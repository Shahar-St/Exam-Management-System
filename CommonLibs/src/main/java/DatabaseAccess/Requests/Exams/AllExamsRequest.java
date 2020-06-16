package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks for all exams from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllExamsRequest extends DatabaseRequest {

    private final String courseID;

    public AllExamsRequest(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseID() {
        return courseID;
    }
}
