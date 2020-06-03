package org.args.GUI;

public class StudentGrade {
    private final String id;
    private final Double grade;

    public StudentGrade(String id, Double grade) {
        this.id = id;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public Double getGrade() {
        return grade;
    }
}
