package hr.persistance.hibernate;

import hr.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EmployeeRepository implements RepositoryInterface<String, Employee> {
    private final SessionFactory sessionFactory;

    public EmployeeRepository() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
    }

    @Override
    public Employee save(Employee entity) {
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
    public Employee delete(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Employee update(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
            return entity;
        }
    }

    @Override
    public Employee findOne(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Employee> result = session.createQuery("select a from Employee a where id=:id")
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
    public List<Employee> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("select a from Employee a")
                    .list();
            session.getTransaction().commit();
            session.close();
            return (List<Employee>) result;
        }
    }
}
