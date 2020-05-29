package org.args.Client;

import java.util.List;

public interface IExamData {
    void saveExam(String title, List<String> questionList,String examId);
}
