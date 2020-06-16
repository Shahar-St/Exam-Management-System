package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExecutedExam;

/**
 * returns details of an executed exam, either to the student or the teacher.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - concrete exam wasn't found
 * 3 - didn't find student's exam
 */

public class GetExecutedExamResponse extends DatabaseResponse {

    private final LightExecutedExam lightExecutedExam;

    public GetExecutedExamResponse(int status, DatabaseRequest request, LightExecutedExam lightExecutedExam) {
        super(status, request);
        this.lightExecutedExam = lightExecutedExam;
    }
    public GetExecutedExamResponse(int error, DatabaseRequest request) {
        this(error, request, null);
    }

    public LightExecutedExam getExam() {
        return lightExecutedExam;
    }
}
