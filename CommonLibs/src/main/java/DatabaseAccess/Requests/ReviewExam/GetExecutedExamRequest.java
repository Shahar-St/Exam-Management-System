package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

public class GetExecutedExamRequest extends DatabaseRequest {

    private final String concreteID;
    private final String studentID;

    public GetExecutedExamRequest(String concreteID, String studentID) {
        this.concreteID = concreteID;
        this.studentID = studentID;
    }

    public String getConcreteID() {
        return concreteID;
    }
    public String getStudentID() {
        return studentID;
    }
}
