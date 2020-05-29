package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExamStatistics;

import java.util.List;

public class ViewReportsResponse extends DatabaseResponse {
    private List<LightExamStatistics> lightExamStatisticsList;

    public ViewReportsResponse(int status, DatabaseRequest request, List<LightExamStatistics> lightExamStatisticsList) {
        super(status, request);
        this.lightExamStatisticsList = lightExamStatisticsList;
    }

    public List<LightExamStatistics> getLightExamStatisticsList() {
        return lightExamStatisticsList;
    }

    public void setLightExamStatisticsList(List<LightExamStatistics> lightExamStatisticsList) {
        this.lightExamStatisticsList = lightExamStatisticsList;
    }
}
