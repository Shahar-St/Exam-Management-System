package DatabaseAccess.Requests.Questions;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks for all questions from a specific course of the logged in user
 * Response: dictionary. key = question id, value = pair{date modified, description}
 */
public class AllQuestionsRequest extends DatabaseRequest {

    private final String course;

    public AllQuestionsRequest(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }
}
