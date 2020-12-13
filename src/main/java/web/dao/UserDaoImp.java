package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

@Repository
public class UserDaoImp implements UserDao{

    private EntityManagerFactory entityManagerFactory;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user) { // TODO шифровать пароль, при регистрации?
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

    @Override
    @SuppressWarnings("unchecked")
    //@Transactional
    public User getByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("FROM User user WHERE user.username =:username");
        List<User> ul = query.setParameter("username", username).getResultList();
        //entityManager.getTransaction().commit();
        if (ul.isEmpty()) {
            return null;
        } else {
            return ul.get(0);
        }
        //return (User) query.getSingleResult();
    }
}
