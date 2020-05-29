package DatabaseAccess.Requests;

import LightEntities.LightQuestion;

public class DeleteQuestionRequest  extends DatabaseRequest {
    private LightQuestion lightQuestion;

    public DeleteQuestionRequest(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }

    public LightQuestion getLightQuestion() {
        return lightQuestion;
    }

    public void setLightQuestion(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }
}
