package DatabaseAccess.Requests.ReviewExam;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * request to view every exam that still contains student exams that were not completely evaluated.
 * **/
public class PendingExamsRequest extends DatabaseRequest {
    public PendingExamsRequest() {
    }
}
