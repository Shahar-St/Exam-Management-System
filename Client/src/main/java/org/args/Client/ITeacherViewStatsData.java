package org.args.Client;

import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITeacherViewStatsData {
    List getTeacherExams();
    Set<String> getSubjects();
    List getCourses(String subjectName);
    void viewExamStatistics(String examId);
    HashMap<String, Double> getCurrentExamForStats();
}
