package edu.school21.sockets.repositories;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Messages;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component  ("ChatroomRepositoryImpl")
public class ChatroomRepositoryImpl implements ChatroomRepository<Chatroom>{
    private DataSource ds;
    private final JdbcTemplate jdbcTemplate;
    public List<Messages> last30Messages () {
        return new ArrayList<>();
    };

    @Autowired
    public  ChatroomRepositoryImpl (@Qualifier("getJdbcImpl") DataSource ds) {
        this.ds=ds;
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public Optional<Chatroom> findById(Long id){
        Chatroom chatroom = null;
        try {
            chatroom = jdbcTemplate.queryForObject(
                    "SELECT * from chats.chatroom where id = ?",
                    (resultSet, rowNum) -> {
                        return new Chatroom(resultSet.getLong(1), resultSet.getString(2),
                                resultSet.getString(3));
                    },
                    id
            );
        }catch(DataAccessException dae) {
            dae.getStackTrace();
        }
        return Optional.ofNullable(chatroom);

    };
    public List<Chatroom> findAll(){
        return  jdbcTemplate.query(
                "SELECT * from chats.chatroom",
                (resultSet, rowNum) ->{
                    return new Chatroom(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3));
                });
    };
    public void save(Chatroom entity){
        jdbcTemplate.update("insert into chats.chatroom (name, owner) values(?,?)",
                 entity.getName(), entity.getOwner()
        );
    };
    public void update(Chatroom entity){
        jdbcTemplate.update("update  chats.chatroom SET name=? owner=? where id=? ",
                entity.getName(), entity.getOwner(), entity.getId());
    };
    public void delete(Long id){
        jdbcTemplate.update("delete from  chats.chatroom  where id=?", id);
    };



}
