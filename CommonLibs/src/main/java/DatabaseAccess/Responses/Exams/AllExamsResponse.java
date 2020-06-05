package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Request: asks for all exams from a specific course of the logDed in user
 * Response: dictionary. key = exam id, value = pair{date modified, title}
 *
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 */
public class AllExamsResponse extends DatabaseResponse {

    private final HashMap<String, Pair<LocalDateTime, String>> examList;

    public AllExamsResponse(int status, DatabaseRequest request,
                                HashMap<String, Pair<LocalDateTime, String>> exams) {
        super(status, request);
        examList = exams;

    }

    public AllExamsResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.examList = null;
    }

    public HashMap<String, Pair<LocalDateTime, String>> getExamList() {
        return examList;
    }
}
