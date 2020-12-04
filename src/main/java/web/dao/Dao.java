package web.dao;

import java.util.List;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

public interface Dao<T> {
    void add(T t);
    T getById(Long id);
    List<T> getAsList();
    void update(Long id, T t);
    void remove(Long id);
    //void clear();
}
