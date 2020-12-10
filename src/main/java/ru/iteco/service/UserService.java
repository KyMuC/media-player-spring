package ru.iteco.service;

import ru.iteco.model.User;

/**
 * User service dealing with user model operations.
 */
public interface UserService {

    /**
     * Add operation for new user.
     * @param user User to add
     */
    void addUser(User user);

    /**
     * Update operation on existing user.
     * @param user User to update
     */
    void updateUser(User user);

    /**
     * Get user by their user name or email.
     * @param query String to lookup
     * @return User with such user name or email of one exists, else - null
     */
    User findByUserNameOrEmail(String query);

    /**
     * Determine whether email is taken by any user.
     * @param email Email to check
     * @return true if email is taken, else - false
     */
    boolean emailTaken(String email);

    /**
     * Determine whether user name is taken by any user.
     * @param userName User name to check
     * @return true if user name is taken, else - false
     */
    boolean userNameTaken(String userName);

    /**
     * Delete operation for user by user name or email.
     * @param query String to lookup
     * @return Deleted user if successful, else - null
     */
    User deleteByUserNameOrEmail(String query);
}
