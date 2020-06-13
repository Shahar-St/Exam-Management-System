package DatabaseAccess.Responses.Statistics;
import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import Util.Pair;
import java.util.HashMap;

public class TeacherStatisticsConcreteResponse extends DatabaseResponse {

    //hashMap: executedExamId, (date, title)
    private final HashMap<String, Pair<String, Double>> executedExamsList;

    public TeacherStatisticsConcreteResponse(int status, DatabaseRequest request,
                                             HashMap<String, Pair<String, Double>> executedExamsList) {

        super(status, request);
        this.executedExamsList = executedExamsList;
    }

    public HashMap<String, Pair<String, Double>> getExecutedExamsList() {
        return executedExamsList;
    }
}
