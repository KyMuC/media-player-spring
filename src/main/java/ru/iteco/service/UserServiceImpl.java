package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iteco.dao.UserDAO;
import ru.iteco.model.User;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        logger.info("user service creation");
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }
}
