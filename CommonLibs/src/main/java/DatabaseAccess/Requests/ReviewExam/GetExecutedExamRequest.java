package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * used to show an entire executed version of an exam. used by teachers (to evaluate) and students (view past exam).
 */
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
