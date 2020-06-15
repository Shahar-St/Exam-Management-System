package Notifiers;

import java.io.Serializable;

/**
 * notify the teacher that a student has raised his hand.
 */

public class RaiseHandNotifier implements Serializable {
    private final String studentName;

    public RaiseHandNotifier(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}
