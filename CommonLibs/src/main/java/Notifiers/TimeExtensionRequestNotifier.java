package Notifiers;

import java.io.Serializable;

/**
 * this is being sent to the dean after a teacher requests time extension
 */

public class TimeExtensionRequestNotifier implements Serializable {

    private final String subjectName;
    private final String courseName;
    private final String teacherFullName;
    private final String examTitle;
    private final String examId;
    private final int durationInMinutes;
    private final String reasonForExtension;

    public TimeExtensionRequestNotifier(String subjectName, String courseName, String teacherFullName,
                                        String examTitle, String examId, int durationInMinutes, String reasonForExtension) {
        this.subjectName = subjectName;
        this.courseName = courseName;
        this.teacherFullName = teacherFullName;
        this.examTitle = examTitle;
        this.examId = examId;
        this.durationInMinutes = durationInMinutes;
        this.reasonForExtension = reasonForExtension;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public String getExamId() {
        return examId;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getReasonForExtension() {
        return reasonForExtension;
    }
}
