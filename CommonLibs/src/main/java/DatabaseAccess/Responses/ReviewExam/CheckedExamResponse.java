package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;

import java.util.HashMap;
import java.util.List;

public class CheckedExamResponse extends DatabaseResponse {
    //HashMap<studentID,isComputerized>
    private final HashMap<String, Boolean> checkedExamsList;

    public CheckedExamResponse(int status, DatabaseRequest request, HashMap<String, Boolean> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }
    public HashMap<String, Boolean> getCheckedExamsList() {
        return checkedExamsList;
    }

}
