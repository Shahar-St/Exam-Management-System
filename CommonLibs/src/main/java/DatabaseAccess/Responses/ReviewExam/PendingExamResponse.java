package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class PendingExamResponse extends DatabaseResponse {
    //HashMap<concreteExamID,Title>
    private final HashMap<Integer, String> checkedExamsList;

    public PendingExamResponse(int status, DatabaseRequest request, HashMap<Integer, String> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }

    public HashMap<Integer, String> getCheckedExamsList() {

        return checkedExamsList;
    }
}
