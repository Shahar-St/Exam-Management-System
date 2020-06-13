package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import java.util.HashMap;

public class GetAllPastExamsResponse extends DatabaseResponse {

    // hashMap: executedExamId, title
    private final HashMap<String, String> executedExamsList;

    public GetAllPastExamsResponse(int status, DatabaseRequest request, HashMap<String, String> executedExamsList) {

        super(status, request);
        this.executedExamsList = executedExamsList;
    }

    public HashMap<String, String> getExecutedExamsList() {
        return executedExamsList;
    }
}

