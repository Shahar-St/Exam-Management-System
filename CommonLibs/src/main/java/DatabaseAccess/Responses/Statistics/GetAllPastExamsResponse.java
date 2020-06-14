package DatabaseAccess.Responses.Statistics;
import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class GetAllPastExamsResponse extends DatabaseResponse {

    // hashMap: executedExamId, (title,grade)
    private final HashMap<String, Pair<String, Double>> executedExamsList;
    private final HashMap<String, LocalDateTime> executedExamsDates;

    public GetAllPastExamsResponse(int status, DatabaseRequest request,
           HashMap<String, Pair<String, Double>> executedExamsList, HashMap<String, LocalDateTime> executedExamsDates) {

        super(status, request);
        this.executedExamsList = executedExamsList;
        this.executedExamsDates = executedExamsDates;
    }

    public HashMap<String, Pair<String, Double>> getExecutedExamsList() {
        return executedExamsList;
    }

    public HashMap<String, LocalDateTime> getExecutedExamsDates() {
        return executedExamsDates;
    }
}

