package ru.iteco.dao;

import ru.iteco.model.User;

import java.util.UUID;

public interface UserDAO extends GenericDAO<UUID, User> {

    /**
     * Get user with a specified email.
     * @param email Email used for lookup
     * @return User with such email if one exists, else - null
     */
    User findByEmail(String email);

    /**
     * Get user with a specified user name or email
     * @param query String used for lookup
     * @return User with such user name or email if one exists, else - null
     */
    User findByUserNameOrEmail(String query);

    /**
     * Determine whether email is taken by any user.
     * @param email Email to check
     * @return true - if email is taken, else - false
     */
    boolean emailExits(String email);

    /**
     * Determine whether user name is taken by any user
     * @param login User name to check
     * @return true - if user name is taken, else - false
     */
    boolean userNameExists(String login);
}
