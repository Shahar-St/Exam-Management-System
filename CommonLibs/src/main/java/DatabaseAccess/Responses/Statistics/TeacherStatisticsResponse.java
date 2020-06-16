package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.util.HashMap;

/**
 * returns results of an executed exam.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class TeacherStatisticsResponse extends DatabaseResponse {

    // hashmap contains data as follows , <studentID, studentExamGrade>
    private final HashMap<String, Double> examHashMap;

    public TeacherStatisticsResponse(int status, DatabaseRequest request, HashMap<String, Double> examHashMap) {
        super(status, request);
        this.examHashMap = examHashMap;
    }
    public TeacherStatisticsResponse(int error, DatabaseRequest request) {
        this(error, request, null);
    }

    public HashMap<String, Double> getStudentGrades() {
        return examHashMap;
    }
}
