package org.args.Client;

import java.util.List;
import java.util.Set;

public interface ITeacherViewStatsData {
    List getTeacherExams();
    Set<String> getSubjects();
    List getCourses(String subjectName);
    void viewExamStatistics(String examId);
}
