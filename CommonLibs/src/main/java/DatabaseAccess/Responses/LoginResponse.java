package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to login a user
 * Response: a string specifies the user permission {student, teacher, dean}
 */
public class LoginResponse extends DatabaseResponse {

    private final String permission;

    // successful request
    public LoginResponse(boolean status, String permission, DatabaseRequest request) {
        super(status, request, null);
        this.permission = permission;
    }

    // unsuccessful request
    public LoginResponse(boolean status, DatabaseRequest request, String errorMsg) {
        super(status, request, errorMsg);
        this.permission = null;
    }
    public String getPermission() {
        return permission;
    }
}