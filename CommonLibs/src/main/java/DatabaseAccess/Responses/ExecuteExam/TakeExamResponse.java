package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

import java.time.LocalDateTime;

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
    private final LocalDateTime initExamForExecutionDate;

    //successful c'tor
    public TakeExamResponse(int status, DatabaseRequest request,
                            LightExam lightExam, LocalDateTime examForExecutionInitDate) {
        super(status, request);
        this.lightExam = lightExam;
        initExamForExecutionDate = examForExecutionInitDate;
    }

    //unsuccessful c'tor
    public TakeExamResponse(int status, DatabaseRequest request) {
        this(status, request, null, null);
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public LocalDateTime getInitExamForExecutionDate() {
        return initExamForExecutionDate;
    }
}
