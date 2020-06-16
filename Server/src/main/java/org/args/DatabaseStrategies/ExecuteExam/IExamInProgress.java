package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.Map;

/**
 * this is being implemented by all class that need to pass messages without a specific request from the same client
 * i.e: exam ended, time extension
 */

public interface IExamInProgress {

    void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                ConnectionToClient client, Session session);
}
