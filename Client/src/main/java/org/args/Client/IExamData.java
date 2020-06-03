package org.args.Client;

import LightEntities.LightQuestion;

import java.util.List;

public interface IExamData {
    void saveExam(String title, List<String> questionList, String examId);

    List<LightQuestion> getLightQuestionListFromCurrentExam();

    String getCurrentExamTitle();

    String getCurrentExamTeacherNotes();

    int getCurrentExamDurationOnMinutes();

    List<Double> getCurrentExamQuestionsScoreList();
}
