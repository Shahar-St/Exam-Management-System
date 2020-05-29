package org.args.Client;

import java.util.List;
import java.util.Set;

public interface IDeanViewStatsData {
    List getExams(String courseName);
    Set<String> getSubjects();
    List getCourses(String subjectName);
    void viewExamStatistics(String examId);
    void viewReport(String report);
}
