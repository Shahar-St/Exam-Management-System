package org.args.Client;

import java.util.List;
import java.util.Set;

public interface IDeanViewStatsData {
    Set<String> getSubjects();
    void viewExamStatistics(String examId);
    void viewReport(String report);
}
