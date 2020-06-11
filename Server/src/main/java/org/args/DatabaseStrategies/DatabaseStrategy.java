package org.args.DatabaseStrategies;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import org.args.Entities.Question;
import org.args.Entities.User;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public abstract class DatabaseStrategy {

    // error codes
    protected final int SUCCESS = 0;
    protected final int UNAUTHORIZED = 1;
    protected final int ERROR2 = 2;
    protected final int ERROR3 = 3;
    protected final int ERROR4 = 4;

    public abstract DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                            Map<String, ConnectionToClient> loggedInUsers);

    protected <T> T getTypeById(Class<T> objectType, String ID, Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(objectType);
        Root<T> root = criteriaQuery.from(objectType);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), ID));
        Query<T> query = session.createQuery(criteriaQuery);
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    protected <T> List<T> getAllOfType(Session session, Class<T> objectType) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(objectType);
        query.from(objectType);
        return session.createQuery(query).getResultList();
    }

    protected User getUser(String userName, Session session) throws NoResultException {

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), userName));
        Query<User> query = session.createQuery(criteriaQuery);
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
