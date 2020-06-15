package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * when a student wants to view a list of all his past exams from a specific course.
 */
public class GetAllPastExamsRequest extends DatabaseRequest {

    private final String courseId;

    public GetAllPastExamsRequest(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}


