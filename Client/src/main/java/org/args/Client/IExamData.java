package org.args.Client;

import DatabaseAccess.Responses.ViewExamResponse;
import LightEntities.LightQuestion;

import java.util.List;

public interface IExamData {
    void saveExam(String title, List<String> questionList,String examId);
    List<LightQuestion> getLightQuestionListFromCurrentExam();
}
