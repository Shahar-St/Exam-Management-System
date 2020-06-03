package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

public class AddExamResponse extends DatabaseResponse {

    public AddExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }

}
