package org.args.Client;

import LightEntities.LightExecutedExam;
import javafx.collections.ObservableList;
import org.args.GUI.StudentExamType;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface IExamReviewData {
    Set<String> getSubjects();
    void confirmGrade(String examId);
    void changeGrade(double newGrade,String reason,String examId);
    void viewExam(String examId);
    void addNote(String note);
    String getCurrentExamId();
    ObservableList<String> getPendingExamsObservableList();
    void showPendingExamGrades(String examId);
    void loadPendingExams();
    File getManualExamForReview();
    String getManualExamForReviewStudentId();
    void saveWordFile(File filePath);
    void submitExamReview(double grade,String notes,String reason, File manualExamFile);
    ObservableList<StudentExamType> getStudentsGradesToReview();
    void reviewExam(String id);
    void clearPendingExams();
    LightExecutedExam getCurrentLightExecutedExam();
    void clearStudentsGradesToReview();
    String getCurrentConcreteExamTitle();
    void setCurrentConcreteExamTitle(String currentConcreteExamTitle);
    String getPendingExamId(String title);
}
