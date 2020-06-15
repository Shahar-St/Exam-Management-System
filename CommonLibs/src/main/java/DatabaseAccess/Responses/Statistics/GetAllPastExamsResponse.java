package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 */

public class GetAllPastExamsResponse extends DatabaseResponse {

    // hashMap: executedExamId, (title,grade)
    private final HashMap<String, Pair<String, Double>> executedExamsList;
    // hashMap: executedExamId, date
    private final HashMap<String, LocalDateTime> executedExamsDates;

    public GetAllPastExamsResponse(int status, DatabaseRequest request,
                                   HashMap<String, Pair<String, Double>> executedExamsList,
                                   HashMap<String, LocalDateTime> executedExamsDates) {

        super(status, request);
        this.executedExamsList = executedExamsList;
        this.executedExamsDates = executedExamsDates;
    }
    public GetAllPastExamsResponse(int error, DatabaseRequest request) {
        this(error, request, null, null);
    }

    public HashMap<String, Pair<String, Double>> getExecutedExamsList() {
        return executedExamsList;
    }

    public HashMap<String, LocalDateTime> getExecutedExamsDates() {
        return executedExamsDates;
    }
}

