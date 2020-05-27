package org.args.Client;

import java.util.List;
import java.util.Set;

public interface IStudentViewStatsData {
    List getExams(String courseName);
    void viewExam(String examId);
    Set<String> getSubjects();
    List getCourses(String subjectName);
    double getAverageGrade(String courseName);
}
