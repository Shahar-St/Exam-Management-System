package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;

import java.util.List;

/**
 * editing an existing exam
 */
public class EditExamRequest extends DatabaseRequest {
    private final String examId;
    private final String examTitle;
    private final List<String> questionsIDs;
    private final List<Double> scoresList;
    private final String teacherNotes;
    private final String studentNotes;
    private final int durationInMinutes;

    public EditExamRequest(String examId, String examTitle, List<String> questionsIDs, List<Double> scoresList,
                           String teacherNotes, String studentNotes, int durationInMinutes) {
        this.examId = examId;
        this.examTitle = examTitle;
        this.questionsIDs = questionsIDs;
        this.scoresList = scoresList;
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.durationInMinutes = durationInMinutes;
    }

    public String getExamId() {
        return examId;
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

    public int getDurationInMinutes() { return durationInMinutes; }
}
