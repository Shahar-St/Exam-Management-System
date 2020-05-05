package DatabaseAccess.Requests;

/**
 * Request: asks to login a user
 * Response: a string specifies the user permission {student, teacher, dean}
 */
public class LoginRequest extends DatabaseRequest {

    private final String userName, password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
