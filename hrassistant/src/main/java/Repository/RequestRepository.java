package Repository;

import Model.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class RequestRepository implements RepositoryInterface<String, Request> {
    private final SessionFactory sessionFactory;

    public RequestRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Request save(Request entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Request delete(Request entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Request update(Request entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Request findOne(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Request> result = session.createQuery("select a from Request a where id=:id")
                    .setParameter("id", id)
                    .list();
            session.getTransaction().commit();
            session.close();
            if (result.size() > 0)
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Request> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Request a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Request>) result;
        }
    }
}
