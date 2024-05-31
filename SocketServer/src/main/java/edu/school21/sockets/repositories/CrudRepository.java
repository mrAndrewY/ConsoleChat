package edu.school21.sockets.repositories;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    public Optional<T> findById(Long id);
    public List<T> findAll();
    public void save(T entity);
    public void update(T entity);
    public void delete(Long id);
}
