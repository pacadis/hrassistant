package Repository;

import Model.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ContractRepository implements RepositoryInterface<String, Contract> {
    private final SessionFactory sessionFactory;

    public ContractRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Contract save(Contract entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contract delete(Contract entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contract update(Contract entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Contract findOne(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Contract> result = session.createQuery("select a from Contract a where usernameEmployee=:username")
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
    public List<Contract> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Contract a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Contract>) result;
        }
    }
}