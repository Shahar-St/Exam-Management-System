package org.args.Client;

import java.util.List;
import java.util.Set;

public interface IExamReviewData {
    Set<String> getSubjects();
    List getCourses(String subjectName);
    List getExams(String courseName);
    void confirmGrade(String examId);
    void changeGrade(double newGrade,String reason,String examId);
    void viewExam(String examId);
    void addNote(String note);
    String getCurrentExamId();
}
