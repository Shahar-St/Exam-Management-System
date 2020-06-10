package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class PendingExamResponse extends DatabaseResponse {
    //HashMap<examID,Pair(Title,Date)>
    private final HashMap<String, Pair<String, LocalDateTime>> checkedExamsList;

    public PendingExamResponse(int status, DatabaseRequest request, HashMap<String, Pair<String, LocalDateTime>> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }

    public HashMap<String, Pair<String, LocalDateTime>> getCheckedExamsList() {

        return checkedExamsList;
    }
}
