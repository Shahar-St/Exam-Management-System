package org.args.DatabaseStrategies.Exams;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Exams.EditExamRequest;
import DatabaseAccess.Requests.Questions.EditQuestionRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Exams.EditExamResponse;
import DatabaseAccess.Responses.Questions.EditQuestionResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Exam;
import org.args.Entities.Question;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EditExamStrategy extends DatabaseStrategy {
    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session, List<String> loggedInUsers) {

        EditExamRequest editExamRequest = (EditExamRequest) request;
        if (client.getInfo("userName") == null)
            return new EditExamResponse(UNAUTHORIZED, request);

        Exam exam = getTypeById(Exam.class, editExamRequest.getExamId(), session);

        if (exam == null)
            return new EditExamResponse(NOT_FOUND, request);

        if (exam.getAuthor() != getUser((String) client.getInfo("userName"), session))
            return new EditExamResponse(NO_ACCESS, request);

        if (!exam.getConcreteExamsList().isEmpty())
        {
            Exam newExam = new Exam(exam);
            session.saveOrUpdate(newExam);
        }
        else
        {
            exam.setDurationInMinutes(editExamRequest.getDurationInMinutes());
            exam.setTitle(editExamRequest.getExamTitle());
            exam.setStudentNotes(editExamRequest.getStudentNotes());
            exam.setTeacherNotes(editExamRequest.getTeacherNotes());
            exam.setQuestionsScores(editExamRequest.getScoresList());

            List<Question> questionsList = new ArrayList<>();
            for (String question : editExamRequest.getQuestionsIDs())
                questionsList.add(getTypeById(Question.class, question, session));
            exam.setQuestionsList(questionsList);

            session.update(exam);
            session.flush();
        }
        return new EditExamResponse(SUCCESS, request);
    }
}
