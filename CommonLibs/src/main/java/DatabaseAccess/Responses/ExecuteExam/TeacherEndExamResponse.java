package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * when the teacher requests to end the exam (time expired).
 */
public class TeacherEndExamResponse extends DatabaseResponse {
    public TeacherEndExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
