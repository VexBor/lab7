package org.vexbor.data;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, ID> {
    T create(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean update(T entity);
    boolean deleteById(ID id);
}
