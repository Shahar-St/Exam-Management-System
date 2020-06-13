package org.args.GUI;

public class StudentPastExam {

    private final String examTitle;
    private final Double grade;

    public StudentPastExam(String examTitle, Double grade) {
        this.examTitle = examTitle;
        this.grade = grade;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public Double getGrade() {
        return grade;
    }
}
