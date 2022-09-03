package demo.service;

import demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    void removeUserById(long id);

    List<User> findAll();

    User findById(long id);

    void update(User user);

    User findByUsername(String name);
}
