package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class DeleteExamResponse extends DatabaseResponse {
    public DeleteExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
