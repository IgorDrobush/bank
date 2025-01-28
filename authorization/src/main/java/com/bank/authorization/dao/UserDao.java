package com.bank.authorization.dao;

import com.bank.authorization.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserByProfileId(long id);

    void createUser(User user);

    User findUserById(long id);

    void updateUser(User user, User userToUpdate);

    void deleteUser(User user);
}
