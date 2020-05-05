package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to login a user
 * Response: a string specifies the user permission {student, teacher, dean}
 */
public class LoginResponse extends DatabaseResponse {

    private final String permission;

    public LoginResponse(boolean status, DatabaseRequest request, String permission) {
        super(status, request);
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
