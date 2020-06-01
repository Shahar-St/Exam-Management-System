package DatabaseAccess.Requests.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightQuestion;

public class DeleteQuestionRequest  extends DatabaseRequest {

    private final String questionID;

    public DeleteQuestionRequest(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionID() {
        return questionID;
    }
}
