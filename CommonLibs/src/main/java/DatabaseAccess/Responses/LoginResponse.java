package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightUser;

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

    private LightUser lightUser;

    public LoginResponse(int status, DatabaseRequest request, LightUser lightUser) {
        super(status, request);
        this.lightUser = lightUser;
    }

    public LightUser getLightUser() {
        return lightUser;
    }

    public void setLightUser(LightUser lightUser) {
        this.lightUser = lightUser;
    }
}