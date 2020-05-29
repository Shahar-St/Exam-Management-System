package org.args.Client;

import java.util.List;
import java.util.Set;

public interface IExamManagementData {
    List getExams(String courseName);
    void editExam(String examId);
    void deleteExam(String examId);
    void addExam(List questionList);
    Set<String> getSubjects();
    List getCourses(String subjectName);
    void viewExam(String examId);
}
