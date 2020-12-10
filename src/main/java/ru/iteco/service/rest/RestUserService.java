package ru.iteco.service.rest;

import ru.iteco.controller.dto.UserDto;

/**
 * User REST service.
 *
 * @author Mikhail_Kudimov
 */
public interface RestUserService {

    /**
     * API for registering a user.
     */
    UserDto registerUser(UserDto userDto);

    /**
     * API for updating a user.
     */
    boolean updateUserInfo(String query, UserDto body);

    /**
     * API for finding user by user name or email.
     */
    UserDto findByUserNameOrEmail(String query);

    /**
     * API for deleting a user.
     */
    boolean removeUser(String query);

}
