package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * returns details of all exams that are yet to be completely evaluated.
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

// returns every concrete exam that has at least one unchecked exam
public class PendingExamsResponse extends DatabaseResponse {

    // hashMap: concreteExamID, (Date,title)
    private final HashMap<String, Pair<LocalDateTime, String>> concreteExamsList;

    public PendingExamsResponse(int status, DatabaseRequest request, HashMap<String, Pair<LocalDateTime, String>> concreteExamsList) {
        super(status, request);
        this.concreteExamsList = concreteExamsList;
    }
    public PendingExamsResponse(int error, DatabaseRequest request) {
        this(error, request, null);
    }

    public HashMap<String, Pair<LocalDateTime, String>> getConcreteExamsList() {

        return concreteExamsList;
    }
}
