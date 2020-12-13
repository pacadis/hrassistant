package Repository;

import Model.Payslip;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class PayslipRepository implements RepositoryInterface<String, Payslip> {
    private final SessionFactory sessionFactory;

    public PayslipRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Payslip save(Payslip entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Payslip delete(Payslip entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Payslip update(Payslip entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Payslip findOne(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Payslip> result = session.createQuery("select a from Payslip a where usernameEmployee=:username")
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
    public List<Payslip> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Payslip a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Payslip>) result;
        }
    }
}