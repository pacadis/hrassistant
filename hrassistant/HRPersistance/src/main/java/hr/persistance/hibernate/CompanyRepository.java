package hr.persistance.hibernate;

import hr.model.Company;
import hr.persistance.JDBCUtils;
import hr.persistance.RepositoryInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CompanyRepository implements RepositoryInterface<String, Company> {
    private static final Logger logger = LogManager.getLogger();
    private JDBCUtils dbUtils;

    public CompanyRepository(Properties props) {
        logger.info("Initializing CompanyRepository with properties: {} ", props);
        dbUtils = new JDBCUtils(props);
    }

    public CompanyRepository() {

    }

    @Override
    public int size() {
        logger.traceEntry();
        logger.traceExit();
        return RepositoryHibernate.getAll(Company.class).size();
    }

    @Override
    public void save(Company entity) {
        logger.traceEntry("saving Company {}", entity);
        RepositoryHibernate.add(Company.class, entity);
        logger.traceExit();

    }

    @Override
    public void delete(String string) {
        logger.traceEntry("deleting Company with {}", string);
        Optional<Company> optional = RepositoryHibernate.get(Company.class, string);
        optional.ifPresent(User -> RepositoryHibernate.delete(Company.class, User));
        logger.traceExit();

    }

    @Override
    public void update(String string, Company entity) {
        logger.traceEntry("updating Company with {}", string);
        Optional<Company> optional = RepositoryHibernate.get(Company.class, string);
        optional.ifPresent(company -> RepositoryHibernate.update(Company.class, company, entity));
        logger.traceExit();
    }

    @Override
    public Company findOne(String string) {
        logger.traceEntry();
        Optional<Company> optional = RepositoryHibernate.get(Company.class, string);
        logger.traceExit();
        return optional.orElse(null);
    }

    @Override
    public List<Company> findAll() {
        logger.traceEntry();
        List<Company> companies = RepositoryHibernate.getAll(Company.class);
        logger.traceExit(companies);
        return companies;
    }
}
