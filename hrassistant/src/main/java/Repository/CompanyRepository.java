package Repository;

import Model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class CompanyRepository implements RepositoryInterface<String, Company> {
    private final SessionFactory sessionFactory;

    public CompanyRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Company save(Company entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (findOne(entity.getId()) != null) {
                session.close();
                return findOne(entity.getId());
            }
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Company delete(Company entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Company update(Company entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Company findOne(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Company> result = session.createQuery("select a from Company a where id=:id")
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
    public List<Company> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Company a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Company>) result;
        }
    }
}
