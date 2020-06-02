package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import LightEntities.LightExam;

public class AddExamResponse extends DatabaseResponse {
    private final LightExam examCreated;

    public AddExamResponse(int status, DatabaseRequest request, LightExam examCreated) {
        super(status, request);
        this.examCreated = examCreated;
    }

    public LightExam getExamCreated() {
        return examCreated;
    }
}
