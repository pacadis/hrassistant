package Repository;

import Model.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ContactRepository implements RepositoryInterface<String, Contact> {
    private final SessionFactory sessionFactory;

    public ContactRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Contact save(Contact entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contact delete(Contact entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contact update(Contact entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contact findOne(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Contact> result = session.createQuery("select a from Contact a where name=:username")
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
    public List<Contact> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Contact a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Contact>) result;
        }
    }
}
