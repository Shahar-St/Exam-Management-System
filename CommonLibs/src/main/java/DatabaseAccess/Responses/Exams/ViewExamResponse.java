package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */
public class ViewExamResponse extends DatabaseResponse {

    private final LightExam lightExam;

    public ViewExamResponse(int status, DatabaseRequest request, LightExam exam) {
        super(status, request);
        lightExam = exam;
    }

    public ViewExamResponse(int status, DatabaseRequest request) {
        this(status, request, null);
    }


    public LightExam getLightExam() {
        return lightExam;
    }

}
