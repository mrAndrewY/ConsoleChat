package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("MessageRepositoryImpl")
public class MessageRepositoryImpl implements MessagesRepository<Messages> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(@Qualifier("getJdbcImpl") DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<Messages> findById(Long id) {
        Messages message = null;
        try {
            message = jdbcTemplate.queryForObject(
                    "select * from chats.messages where id=?",
                    (resultSet, rowNum) -> {
                        return new Messages(resultSet.getLong(1), resultSet.getLong(2),
                                resultSet.getLong(3), resultSet.getString(4));
                    },
                    id
            );

        } catch (DataAccessException dae) {
            dae.getStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Messages> findAll() {
        return jdbcTemplate.query("select * from chats.messages",
                (resultSet, rowNum) -> {
                    return new Messages(resultSet.getLong(1), resultSet.getLong(2),
                            resultSet.getLong(3), resultSet.getString(4));
                }
        );
    }

    @Override
    public void save(Messages entity) {
        jdbcTemplate.update("insert into chats.messages (chatroomId, senderId, text) values (?, ? , ?)",
                entity.getChatroomId(), entity.getSenderId(), entity.getText());
    }

    @Override
    public void update(Messages entity) {
        jdbcTemplate.update("update chats.messages set chatroomId=? senderId=? text=? where id=?",
                entity.getChatroomId(), entity.getSenderId(), entity.getText(), entity.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from chats.messages where id=?", id);
    }

    @Override
    public List<Messages> last30Messages() {
        return jdbcTemplate.query("select * from (select * from chats.messages order by id desc limit 30) as new order by id asc",
                (resultSet, rowNum) -> {
                    return new Messages(resultSet.getLong(1), resultSet.getLong(2),
                            resultSet.getLong(3), resultSet.getString(4));
                }
        );
    }
}
