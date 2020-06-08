package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

public class RaiseHandResponse extends DatabaseResponse {
    private final String studentName;
    public RaiseHandResponse(int status, DatabaseRequest request, String studentName)
    {
        super(status, request);
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}
