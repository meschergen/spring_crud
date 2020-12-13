package web.dao;

import web.model.Role;

import java.util.List;

/**
 * 12.12.2020
 *
 * @author MescheRGen
 */

public interface RoleDao extends  Dao<Role>{
    @Override
    void add(Role role);

    @Override
    List<Role> getAsList();

    @Override
    void remove(Long id);

    @Override
    Role getById(Long id);

    @Override
    void update(Long id, Role role);
}
