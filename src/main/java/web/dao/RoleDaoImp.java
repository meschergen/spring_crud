package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * 12.12.2020
 *
 * @author MescheRGen
 */

@Repository
public class RoleDaoImp implements RoleDao{

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public void add(Role role) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(role);
        entityManager.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAsList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("FROM Role").getResultList();
    }

    @Transactional
    @Override
    public void remove(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
        entityManager.getTransaction().commit();
    }

    @Override
    public Role getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Role.class, id);
    }

    @Transactional
    @Override
    public void update(Long id, Role role) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(role);
        entityManager.getTransaction().commit();
    }
}
