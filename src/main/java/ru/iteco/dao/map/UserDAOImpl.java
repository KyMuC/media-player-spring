package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.UserDAO;
import ru.iteco.model.User;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class UserDAOImpl extends AbstractDAO<UUID, User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class, new HashMap<>());
    }


    @Override
    public User findByEmail(String email) {
        return items.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public User findByUserNameOrEmail(String query) {
        return items.values().stream().filter(user -> user.getEmail().equals(query) ||
                user.getUserName().equals(query)).findFirst().orElse(null);
    }

    @Override
    public boolean emailExits(String email) {
        return items.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean userNameExists(String login) {
        return items.values().stream().anyMatch(user -> user.getUserName().equals(login));
    }
}
