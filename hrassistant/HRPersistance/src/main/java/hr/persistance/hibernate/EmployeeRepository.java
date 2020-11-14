package hr.persistance.hibernate;

import hr.model.Employee;
import hr.persistance.JDBCUtils;
import hr.persistance.RepositoryInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class EmployeeRepository implements RepositoryInterface<String, Employee> {
    private static final Logger logger = LogManager.getLogger();
    private JDBCUtils dbUtils;

    public EmployeeRepository(Properties props) {
        logger.info("Initializing EmployeeRepository with properties: {} ", props);
        dbUtils = new JDBCUtils(props);
    }

    public EmployeeRepository() {

    }

    @Override
    public int size() {
        logger.traceEntry();
        logger.traceExit();
        return RepositoryHibernate.getAll(Employee.class).size();
    }

    @Override
    public void save(Employee entity) {
        logger.traceEntry("saving Employee {}", entity);
        RepositoryHibernate.add(Employee.class, entity);
        logger.traceExit();

    }

    @Override
    public void delete(String string) {
        logger.traceEntry("deleting Employee with {}", string);
        Optional<Employee> optional = RepositoryHibernate.get(Employee.class, string);
        optional.ifPresent(User -> RepositoryHibernate.delete(Employee.class, User));
        logger.traceExit();

    }

    @Override
    public void update(String string, Employee entity) {
        logger.traceEntry("updating Employee with {}", string);
        Optional<Employee> optional = RepositoryHibernate.get(Employee.class, string);
        optional.ifPresent(employee -> RepositoryHibernate.update(Employee.class, employee, entity));
        logger.traceExit();
    }

    @Override
    public Employee findOne(String string) {
        logger.traceEntry();
        Optional<Employee> optional = RepositoryHibernate.get(Employee.class, string);
        logger.traceExit();
        return optional.orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        logger.traceEntry();
        List<Employee> employees = RepositoryHibernate.getAll(Employee.class);
        logger.traceExit(employees);
        return employees;
    }
}
