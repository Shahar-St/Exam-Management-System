package DatabaseAccess.Responses.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

import java.util.HashMap;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class UncheckedExecutesOfConcreteResponse extends DatabaseResponse {

    //HashMap<studentID, isComputerized>
    private final HashMap<String, Boolean> checkedExamsList;

    public UncheckedExecutesOfConcreteResponse(int status, DatabaseRequest request, HashMap<String, Boolean> checkedExamsList) {
        super(status, request);
        this.checkedExamsList = checkedExamsList;
    }

    public UncheckedExecutesOfConcreteResponse(int error, DatabaseRequest request) {
        this(error, request, null);
    }


    public HashMap<String, Boolean> getCheckedExamsList() {
        return checkedExamsList;
    }

}
