package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class TeacherGetAllPastExamsRequest extends DatabaseRequest {

    private final String courseId;

    public TeacherGetAllPastExamsRequest(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
