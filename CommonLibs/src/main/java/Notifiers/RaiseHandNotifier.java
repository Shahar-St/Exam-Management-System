package Notifiers;

import java.io.Serializable;

public class RaiseHandNotifier implements Serializable {
    private final String studentName;

    public RaiseHandNotifier(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}
