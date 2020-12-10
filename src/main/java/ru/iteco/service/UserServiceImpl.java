package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.dao.UserDAO;
import ru.iteco.model.User;

@Service
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

    @Override
    public User findByUserNameOrEmail(String query) {
        return userDAO.findByUserNameOrEmail(query);
    }

    @Override
    public boolean emailTaken(String email) {
        return userDAO.emailExits(email);
    }

    @Override
    public boolean userNameTaken(String userName) {
        return userDAO.userNameExists(userName);
    }

    @Override
    public User deleteByUserNameOrEmail(String query) {
        User user = findByUserNameOrEmail(query);

        if (user == null) return null;

        return userDAO.deleteByKey(user.getId());
    }
}
