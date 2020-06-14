package org.args.GUI;

public class StudentPastExam {

    private final String examTitle;
    private final String examId;
    private final Double grade;
    private final String date;

    public StudentPastExam(String examTitle, String examId, Double grade, String date) {
        this.examTitle = examTitle;
        this.examId = examId;
        this.grade = grade;
        this.date = date;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public Double getGrade() {
        return grade;
    }

    public String getExamId() {
        return examId;
    }

    public String getDate() {
        return date;
    }
}
