package DatabaseAccess.Responses.Statistics;
import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;
import java.util.HashMap;

public class GetAllPastExamsResponse extends DatabaseResponse {

    // hashMap: executedExamId, (title,grade)
    private final HashMap<String, Pair<String, Double>> executedExamsList;

    public GetAllPastExamsResponse(int status, DatabaseRequest request, HashMap<String, Pair<String, Double>> executedExamsList) {

        super(status, request);
        this.executedExamsList = executedExamsList;
    }

    public HashMap<String, Pair<String, Double>> getExecutedExamsList() {
        return executedExamsList;
    }
}

