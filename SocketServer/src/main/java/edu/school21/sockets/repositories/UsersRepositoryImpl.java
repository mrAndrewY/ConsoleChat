package edu.school21.sockets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("UsersRepositoryImpl")
public class UsersRepositoryImpl implements UsersRepository<User> {
    @Autowired
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(@Qualifier("getJdbcImpl") DataSource ds) {
        this.ds = ds;
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public UsersRepositoryImpl getUsersRepositoryJdbcTemplateImpl() {
        return new UsersRepositoryImpl(ds);
    }

    public Optional<User> findByName(String name) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(
                    "SELECT id, name, password from chats.users where name = ?",
                    (resultSet, rowNum) -> new User(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3)),
                    name
            );
        } catch (DataAccessException dae) {
            dae.getStackTrace();
        }
        return Optional.ofNullable(user);
    }


    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(
                    "SELECT id, name, password from chats.users where id = ?",
                    (resultSet, rowNum) -> {
                        return new User(resultSet.getLong(1), resultSet.getString(2),
                                resultSet.getString(3));
                    },
                    id
            );
        } catch (DataAccessException dae) {
            dae.getStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> usersList = jdbcTemplate.query(
                "SELECT id, mail, password from chats.users",
                (resultSet, rowNum) -> {
                    return new User(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3));
                });
        return usersList;
    }


    @Override
    public void save(User entity) {
        jdbcTemplate.update("insert into chats.users (name, password) values(?, ?)",
                entity.getName(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("update  chats.users SET name=? password=? where id=? ", entity.getName(),
                entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE from  chats.users  where id=?", id);
    }
}

