package org.args.GUI;

//created for showing a table of students and their performed exam type.

public class StudentExamType {
    private final String id;
    private final String examType;

    public StudentExamType(String id, String examType)
    {
        this.id = id;
        this.examType = examType;
    }

    public String getId() {
        return id;
    }

    public String getExamType() {
        return examType;
    }
}
