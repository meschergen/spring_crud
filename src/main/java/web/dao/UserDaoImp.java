package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

@Repository
public class UserDaoImp implements UserDao{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void add(User user) {

    }

    @Override
    public List<User> getAsList() {
        List<User> li = new ArrayList<>();
        li.add(new User(1L, "Ivan1", "Ivanov1","email_1"));
        li.add(new User(2L, "Ivan2", "Ivanov2","email_2"));
        li.add(new User(3L, "Ivan3", "Ivanov3","email_3"));
        li.add(new User(4L, "Ivan4", "Ivanov4","email_4"));
        return li;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public User getById(Long id) {
        return new User(id,"Ivan" + id, "Ivanov" + id,"email_" + id);
    }

    @Override
    public void update(Long id, User user) {

    }
}
