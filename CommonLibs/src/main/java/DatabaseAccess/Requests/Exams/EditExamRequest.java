package DatabaseAccess.Requests.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightExam;

import java.util.List;

public class EditExamRequest extends DatabaseRequest {
    private final String examId;
    private final String examTitle;
    private final List<String> questionsIDs;
    private final List<Double> scoresList;
    private final String teacherNotes;
    private final String studentNotes;

    public EditExamRequest(String examId, String examTitle, List<String> questionsIDs, List<Double> scoresList, String teacherNotes, String studentNotes) {
        this.examId = examId;
        this.examTitle = examTitle;
        this.questionsIDs = questionsIDs;
        this.scoresList = scoresList;
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
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
}
