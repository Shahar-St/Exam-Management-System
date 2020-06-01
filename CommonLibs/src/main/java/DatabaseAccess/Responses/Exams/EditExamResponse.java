package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class EditExamResponse extends DatabaseResponse {
    public EditExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
