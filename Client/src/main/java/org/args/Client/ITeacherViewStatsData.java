package org.args.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITeacherViewStatsData {
    List getTeacherExams();
    Set<String> getSubjects();
    void viewExamStatistics(String examId);
    HashMap<String, Double> getCurrentExamForStats();
}
