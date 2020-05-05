package Entities;

import javax.persistence.Entity;

@Entity
public class Teacher extends User {

    public Teacher() {
    }

    public Teacher(int socialId, String firstName, String lastName, String password, String userName) {
        super(socialId, firstName, lastName, password, userName);
        this.setPermissionLevel("TeacherPermission");
    }
}
