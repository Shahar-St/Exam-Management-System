package DatabaseAccess.Requests;

import LightEntities.LightQuestion;

public class AddQuestionRequest extends DatabaseRequest {
    private LightQuestion lightQuestion;

    public AddQuestionRequest(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }

    public LightQuestion getLightQuestion() {
        return lightQuestion;
    }

    public void setLightQuestion(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }
}
