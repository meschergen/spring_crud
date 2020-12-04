package web.dao;

import web.model.User;

import java.util.List;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

public interface UserDao extends Dao<User> {

    @Override
    void add(User user);

    @Override
    List<User> getAsList();

    @Override
    void remove(User user);

    @Override
    User getById(Long id);
}