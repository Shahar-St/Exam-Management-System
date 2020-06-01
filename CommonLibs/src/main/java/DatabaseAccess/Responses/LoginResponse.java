package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to login a user
 * Response: a string specifies the user permission {student, teacher, dean}
 *
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - username wasn't found
 *  3 - user is currently logged in from a different terminal"
 *  4 - wrong password
 */
public class LoginResponse extends DatabaseResponse {

    private final String id;
    private final String name;
    private final String permission;

    public LoginResponse(int status, DatabaseRequest request, String id, String name, String permission) {
        super(status, request);
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }
}