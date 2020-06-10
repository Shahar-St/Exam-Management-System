package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - the student don't have any exam to take
 * 3 - wrong exam code
 * 4 - wrong ID
 */

public class TakeExamResponse extends DatabaseResponse {

    private final LightExam lightExam;

    //successful c'tor
    public TakeExamResponse(int status, DatabaseRequest request, LightExam lightExam) {
        super(status, request);
        this.lightExam = lightExam;
    }

    //unsuccessful c'tor
    public TakeExamResponse(int status, DatabaseRequest request) {
        this(status, request, null);
    }

    public LightExam getLightExam() {
        return lightExam;
    }
}
