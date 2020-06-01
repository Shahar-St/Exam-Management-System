package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

public class ViewExamResponse extends DatabaseResponse {
    private final LightExam lightExam;

    public ViewExamResponse(int status, DatabaseRequest request,LightExam exam) {
        super(status, request);
        lightExam = exam;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

}
