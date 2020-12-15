package Repository;

import Model.Holiday;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class HolidayRepository implements RepositoryInterface<String, Holiday> {
    private final SessionFactory sessionFactory;

    public HolidayRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Holiday save(Holiday entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Holiday delete(Holiday entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Holiday update(Holiday entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Holiday findOne(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Holiday> result = session.createQuery("select a from Holiday a where usernameEmployee=:username")
                    .setParameter("username", username)
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
    public List<Holiday> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Holiday a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Holiday>) result;
        }
    }

    public Holiday findOneByIdRequest(String idRequest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Holiday> result = session.createQuery("select a from Holiday a where idRequest=:idRequest")
                    .setParameter("idRequest", idRequest)
                    .list();
            session.getTransaction().commit();
            session.close();
            if (result.size() > 0)
                return result.get(0);
            else
                return null;
        }
    }
}