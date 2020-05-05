package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * request: get the all subjects and courses the user have read access to
 * <p>
 * response:
 * String[] subjects
 * String[] courses
 */
public class SubjectsAndQuestionResponse extends DatabaseResponse {

    private final String[] subjects, courses;

    public SubjectsAndQuestionResponse(boolean status, DatabaseRequest request, String[] subjects, String[] courses) {
        super(status, request);
        this.subjects = subjects;
        this.courses = courses;
    }

    public String[] getSubjects() {
        return subjects;
    }
    public String[] getCourses() {
        return courses;
    }
}
