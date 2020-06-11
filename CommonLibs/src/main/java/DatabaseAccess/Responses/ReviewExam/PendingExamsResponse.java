package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

// returns every concrete exam that has at least one unchecked exam
public class PendingExamsResponse extends DatabaseResponse {
    //HashMap<concreteExamID, examTitle>
    private final HashMap<Integer, String> checkedExamsList;

    public PendingExamsResponse(int status, DatabaseRequest request, HashMap<Integer, String> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }

    public HashMap<Integer, String> getCheckedExamsList() {

        return checkedExamsList;
    }
}
