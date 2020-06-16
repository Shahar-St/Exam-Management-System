package org.args.Client;

import LightEntities.LightExecutedExam;
import javafx.collections.ObservableList;
import org.args.GUI.StudentExamType;

import java.io.File;

public interface IExamReviewData {

    ObservableList<String> getPendingExamsObservableList();

    void showPendingExamGrades(String examId);

    void loadPendingExams();

    String getManualExamForReviewStudentId();

    void saveWordFile(File filePath);

    void submitExamReview(double grade, String notes, String reason, File manualExamFile);

    ObservableList<StudentExamType> getStudentsGradesToReview();

    void reviewExam(String id);

    void clearPendingExams();

    LightExecutedExam getCurrentLightExecutedExam();

    void clearStudentsGradesToReview();

    String getCurrentConcreteExamTitle();

    void setCurrentConcreteExamTitle(String currentConcreteExamTitle);

    String getPendingExamId(String title);
}
