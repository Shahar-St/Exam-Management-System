package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import org.args.ExamManager;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.Map;

public interface IExamInProgress {

    void handle(DatabaseRequest request, DatabaseResponse response, Map<Integer, ExamManager> examManagers,
                ConnectionToClient client, Session session);
}
