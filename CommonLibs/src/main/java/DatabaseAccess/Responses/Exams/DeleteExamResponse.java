package DatabaseAccess.Responses.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - Exam wasn't found
 * 3 - user isn't the exam's author
 * 4 - Exam is part of an exam
 */

public class DeleteExamResponse extends DatabaseResponse {
    public DeleteExamResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}
