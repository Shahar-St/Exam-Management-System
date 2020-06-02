package org.args.DatabaseStrategies.QuestionStrategy;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.Questions.AllQuestionsRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.Questions.AllQuestionsResponse;
import Util.Pair;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.Course;
import org.args.Entities.Question;
import org.args.Entities.Teacher;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllQuestionsStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session) {

        AllQuestionsRequest allQuestionsRequest = (AllQuestionsRequest) request;

        HashMap<String, Pair<LocalDateTime, String>> map = new HashMap<>();

        if (client.getInfo("userName") == null)
            return new AllQuestionsResponse(UNAUTHORIZED, request);

        User user = getUser((String) client.getInfo("userName"), session);

        if (user == null)
            return new AllQuestionsResponse(NOT_FOUND, request);


        List<Question> questionList = new ArrayList<>();
        if (user instanceof Teacher)
        {
            Teacher teacher = (Teacher) user;
            for (Course tCourse : teacher.getCoursesList())
            {
                if (tCourse.getName().equals(allQuestionsRequest.getCourse()))
                    questionList.addAll(tCourse.getQuestionsList());
            }
        }
        else    // user is dean
        {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
            Root<Question> root = criteriaQuery.from(Question.class);
            criteriaQuery.select(root);
            Query<Question> query = session.createQuery(criteriaQuery);
            questionList.addAll(query.getResultList());
        }

        for (Question question : questionList)
            map.put(question.getId(),
                    new Pair<>(question.getLastModified(), question.getQuestionContent()));

        return new AllQuestionsResponse(SUCCESS, request, map);
    }
}
