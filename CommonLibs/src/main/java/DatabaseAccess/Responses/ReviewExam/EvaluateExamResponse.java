package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class EvaluateExamResponse extends DatabaseResponse {

    public EvaluateExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
