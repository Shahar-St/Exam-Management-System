package DatabaseAccess.Responses.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.util.HashMap;
import java.util.List;

public class CheckedExamResponse extends DatabaseResponse {
    //HashMap<studentID,Pair(answersList,Grade)>
    private final HashMap<String, Pair<List<Integer>,Double>> checkedExamsList;

    public CheckedExamResponse(int status, DatabaseRequest request, HashMap<String, Pair<List<Integer>, Double>> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }

    public HashMap<String, Pair<List<Integer>, Double>> getCheckedExamsList() {
        return checkedExamsList;
    }
}
