package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * returns all past exam executions of exams written by requesting teacher.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class TeacherGetAllPastExamsResponse extends DatabaseResponse {

    //hashMap: concreteExamId, (date, title)
    private final HashMap<String, Pair<LocalDateTime, String>> concreteExamsList;

    public TeacherGetAllPastExamsResponse(int status, DatabaseRequest request,
                                          HashMap<String, Pair<LocalDateTime, String>> concreteExamsList) {

        super(status, request);
        this.concreteExamsList = concreteExamsList;
    }

    public HashMap<String, Pair<LocalDateTime, String>> getConcreteExamsList() {
        return concreteExamsList;
    }
}
