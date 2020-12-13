package Repository;

import Model.Clocking;
import Model.Holiday;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ClockingRepository implements RepositoryInterface<String, Clocking> {
    private final SessionFactory sessionFactory;

    public ClockingRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Clocking save(Clocking entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Clocking delete(Clocking entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Clocking update(Clocking entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Clocking findOne(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Clocking> result = session.createQuery("select a from Clocking a where usernameEmployee=:username")
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
    public List<Clocking> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Clocking a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Clocking>) result;
        }
    }
    public Clocking findOneByIdRequest(String idRequest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Clocking> result = session.createQuery("select a from Clocking a where idRequest=:idRequest")
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