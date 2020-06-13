package org.args.GUI;

public class StudentPastExam {

    private final String examTitle;
    private final String examId;
    private final Double grade;

    public StudentPastExam(String examTitle, String examId, Double grade) {
        this.examTitle = examTitle;
        this.examId = examId;
        this.grade = grade;
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
}
