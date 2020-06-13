package DatabaseAccess.Requests.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;

public class GetAllPastExamsRequest extends DatabaseRequest {

    private final String courseId;

    public GetAllPastExamsRequest(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}


