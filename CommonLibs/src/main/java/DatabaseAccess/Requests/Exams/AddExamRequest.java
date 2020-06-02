package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExam;

import java.util.List;

public class AddExamRequest extends DatabaseRequest {

    private final String examTitle;
    private final List<String> questionsIDs;
    private final List<Double> scoresList;
    private final String teacherNotes;
    private final String studentNotes;
    private final int durationInMinutes;
    private final String description;
    private final String courseID;

    public AddExamRequest(String examTitle, List<String> questionsIDs, List<Double> scoresList, String teacherNotes,
                          String studentNotes, int durationInMinutes, String description, String courseID) {
        this.examTitle = examTitle;
        this.questionsIDs = questionsIDs;
        this.scoresList = scoresList;
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.durationInMinutes = durationInMinutes;
        this.description = description;
        this.courseID = courseID;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public List<String> getQuestionsIDs() {
        return questionsIDs;
    }

    public List<Double> getScoresList() {
        return scoresList;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseID() {
        return courseID;
    }
}
