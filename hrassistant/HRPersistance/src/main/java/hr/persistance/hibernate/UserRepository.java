package hr.persistance.hibernate;

import hr.model.User;
import hr.persistance.JDBCUtils;
import hr.persistance.RepositoryInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserRepository implements RepositoryInterface<String, User> {
    private static final Logger logger = LogManager.getLogger();
    private JDBCUtils dbUtils;

    public UserRepository(Properties props) {
        logger.info("Initializing UserRepository with properties: {} ", props);
        dbUtils = new JDBCUtils(props);
    }

    public UserRepository() {

    }

    @Override
    public int size() {
        logger.traceEntry();
        logger.traceExit();
        return RepositoryHibernate.getAll(User.class).size();
    }

    @Override
    public void save(User entity) {
        logger.traceEntry("saving User {}", entity);
        RepositoryHibernate.add(User.class, entity);
        logger.traceExit();

    }

    @Override
    public void delete(String string) {
        logger.traceEntry("deleting User with {}", string);
        Optional<User> optional = RepositoryHibernate.get(User.class, string);
        optional.ifPresent(User -> RepositoryHibernate.delete(User.class, User));
        logger.traceExit();
    }

    @Override
    public void update(String string, User entity) {
        logger.traceEntry("updating User with {}", string);
        Optional<User> optional = RepositoryHibernate.get(User.class, string);
        optional.ifPresent(user -> RepositoryHibernate.update(User.class, user, entity));
        logger.traceExit();
    }

    @Override
    public User findOne(String string) {
        logger.traceEntry();
        Optional<User> optional = RepositoryHibernate.get(User.class, string);
        logger.traceExit();
        return optional.orElse(null);
    }

    @Override
    public List<User> findAll() {
        logger.traceEntry();
        List<User> users = RepositoryHibernate.getAll(User.class);
        logger.traceExit(users);
        return users;
    }
}
