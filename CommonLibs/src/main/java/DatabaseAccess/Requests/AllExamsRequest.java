package DatabaseAccess.Requests;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllExamsRequest extends DatabaseRequest {

    private final String course;

    public AllExamsRequest(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }
}
