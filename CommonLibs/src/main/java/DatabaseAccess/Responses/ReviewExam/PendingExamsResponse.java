package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

// returns every concrete exam that has at least one unchecked exam
public class PendingExamsResponse extends DatabaseResponse {

    // hashMap: concreteExamID, (title,grade)
    private final HashMap<String, Pair<LocalDateTime, String>> concreteExamsList;

    public PendingExamsResponse(int status, DatabaseRequest request, HashMap<String, Pair<LocalDateTime, String>> concreteExamsList) {
        super(status, request);
        this.concreteExamsList = concreteExamsList;
    }

    public HashMap<String, Pair<LocalDateTime, String>> getConcreteExamsList() {

        return concreteExamsList;
    }
}
