package ru.iteco.dao;

import ru.iteco.model.User;

import java.util.UUID;

public interface UserDAO extends GenericDAO<UUID, User> {

    User findByEmail(String email);

    User findByUserNameOrEmail(String query);

    boolean emailExits(String email);

    boolean loginExists(String login);
}
