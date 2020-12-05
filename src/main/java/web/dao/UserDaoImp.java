package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    @Transactional
    @Override
    public void add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAsList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("FROM User").getResultList();
    }

    @Transactional
    @Override
    public void remove(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }
}
