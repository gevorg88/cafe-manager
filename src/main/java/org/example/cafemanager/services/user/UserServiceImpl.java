package org.example.cafemanager.services.user;

import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.dto.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserUpdateRequestBody;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.SecurityUtility;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User findByEmail(@NotNull String email) {
        Assert.notNull(email, "Empty Email provided");
        LOG.info("retrieving user with email  {}", email);
        return userRepo.findUsersByEmail(email);
    }

    @Override
    public User findByUsername(@NotNull String username) {
        Assert.notNull(username, "Empty Username provided");
        return userRepo.findUsersByUsername(username);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(UserCreate userCreate, Role role) {
        if (findByUsername(userCreate.getUsername()) != null) {
            throw new MustBeUniqueException("username");
        }

        if (findByEmail(userCreate.getEmail()) != null) {
            throw new MustBeUniqueException("email");
        }

        User user = new User();
        user.setUsername(userCreate.getUsername());
        user.setEmail(userCreate.getEmail());
        user.setPassword(SecurityUtility.passwordEncoder().encode(userCreate.getPassword()));
        user.setFirstName(userCreate.getFirstName());
        user.setLastName(userCreate.getLastName());
        user.setRole(role);
        save(user);
        return user;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public Collection<UserPublicProps> getAllWaiters() {
        return this.userRepo.findAllByRole(Role.WAITER);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findUserById(id);
    }

    @Override
    public User update(Long id, UserUpdateRequestBody requestBody) {
        User user = userRepo.findUserById(id);
        if (null == user) {
            throw new InstanceNotFoundException("user");
        }

        if (!user.getFirstName().equals(requestBody.getFirstName())){
            user.setFirstName(requestBody.getFirstName());
        }

        if (!user.getLastName().equals(requestBody.getLastName())){
            user.setLastName(requestBody.getLastName());
        }
        userRepo.save(user);

        return user;
    }
}
