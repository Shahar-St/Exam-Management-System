package Entities;

import javax.persistence.Entity;

@Entity
public class Student extends User {

    private StudentExam studentExam;

    public Student() {
    }

    public Student(int socialId, String firstName, String lastName, String password, String userName) {
        super(socialId, firstName, lastName, password, userName);
        this.setPermissionLevel("StudentPermission");
    }
}
