package demo.service;

import demo.model.Role;
import demo.model.User;
import demo.repository.RoleRepository;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        //user.addRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    //@Override
   // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //    return null;
    //}

     @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("User " + username + " not found !!!");
        }
        return user;
    }
}
