package org.example.cafemanager.services.user;

import org.example.cafemanager.accessData.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.SecurityUtility;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User findByEmail(@NotNull String email) {
        Assert.notNull(email, "Empty Email provided");
        LOG.info("retrieving user with email  {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByEmail(username);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(UserCreate userCreate, Role role) {
        User user = new User();
        user.setUserName(userCreate.getUsername());
        user.setEmail(userCreate.getEmail());
        user.setPassword(SecurityUtility.passwordEncoder().encode(userCreate.getPassword()));
        user.setRole(role);
        save(user);
        return user;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }
}
