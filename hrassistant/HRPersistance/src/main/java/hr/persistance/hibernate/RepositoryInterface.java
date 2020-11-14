package hr.persistance.hibernate;

import java.util.List;

public interface RepositoryInterface<ID, E> {
    E save(E entity);

    E delete(E entity);

    E update(E entity);

    E findOne(ID id);

    List<E> findAll();
}