package hr.persistance.hibernate;

import hr.model.Company;
import hr.model.Employee;
import hr.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class RepositoryHibernate {
    private static final SessionFactory factory = new Configuration()
            .configure()
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Employee.class)
            .addAnnotatedClass(Company.class)
            .buildSessionFactory();

    public RepositoryHibernate() {
    }

    public static <T> void add(Class<T> classz, T obj) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(obj);
        tx.commit();

        session.close();
    }

    public static <T> Optional<T> get(Class<T> classz, String key) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        T obj = session.get(classz, key);
        tx.commit();

        session.close();
        if (obj == null)
            return Optional.empty();
        else
            return Optional.of(obj);
    }

    public static <T> void delete(Class<T> classz, T obj) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.delete(obj);
        tx.commit();

        session.close();
    }

    public static <T> void update(Class<T> classz, T oldObject, T newObject) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(newObject);
        tx.commit();

        session.close();
    }

    public static <T> List<T> getAll(final Class<T> classz) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        List<T> all = session.createCriteria(classz).list();
        tx.commit();

        session.close();
        return all;
    }
}
