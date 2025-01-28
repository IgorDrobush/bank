package com.bank.authorization.dao;

import com.bank.authorization.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImpl() {

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUserByProfileId(long id) {
        String query = "SELECT u FROM User u WHERE u.profileId = :profile_id";
        return entityManager.createQuery(query, User.class)
                .setParameter("profile_id", id)
                .getSingleResult();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user, User userToUpdate) {
        userToUpdate.setRole(user.getRole());
        userToUpdate.setProfileId(user.getProfileId());
        userToUpdate.setPassword(user.getPassword());
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }
}
