package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.util.HashMap;
import java.util.List;

public class TeacherStatisticsResponse extends DatabaseResponse {
    // hashmap contains data as follows , <studentID,studentExamGrade>
    private final HashMap<String, Double> examHashMap;

    public TeacherStatisticsResponse(int status, DatabaseRequest request, HashMap<String, Double> examHashMap) {
        super(status, request);
        this.examHashMap = examHashMap;
    }

    public HashMap<String, Double> getStudentGrades() {
        return examHashMap;
    }
}
