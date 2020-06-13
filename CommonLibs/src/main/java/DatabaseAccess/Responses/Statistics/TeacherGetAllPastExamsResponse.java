package DatabaseAccess.Responses.Statistics;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;
import java.time.LocalDateTime;
import java.util.HashMap;

public class TeacherGetAllPastExamsResponse extends DatabaseResponse {

    //hashMap: concreteExamId, (date, title)
    private final HashMap<String, Pair<LocalDateTime, String>> concreteExamsList;

    public TeacherGetAllPastExamsResponse(int status, DatabaseRequest request,
                                          HashMap<String, Pair<LocalDateTime, String>> concreteExamsList) {

        super(status, request);
        this.concreteExamsList = concreteExamsList;
    }

    public HashMap<String, Pair<LocalDateTime, String>> getConcreteExamsList() {
        return concreteExamsList;
    }
}
