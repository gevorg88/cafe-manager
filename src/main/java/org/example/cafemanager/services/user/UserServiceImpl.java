package org.example.cafemanager.services.user;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.dto.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserUpdateRequestBody;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.repositories.OrderRepository;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.table.contracts.TableService;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.SecurityUtility;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final OrderRepository orderRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepo, OrderRepository orderRepository) {
        this.userRepo = userRepo;
        this.orderRepository = orderRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findUsersByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
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

        if (!user.getFirstName().equals(requestBody.getFirstName())) {
            user.setFirstName(requestBody.getFirstName());
        }

        if (!user.getLastName().equals(requestBody.getLastName())) {
            user.setLastName(requestBody.getLastName());
        }
        userRepo.save(user);

        return user;
    }

    @Override
    @Transactional
    public void destroyUser(Long userId) {
        User user = userRepo.findUserById(userId);

        if (null == user) {
            throw new InstanceNotFoundException("user");
        }

        Set<CafeTable> assignedTables = user.getTables();
        Set<Order> orders = new HashSet<>();
        for (CafeTable table : assignedTables) {
            orders.addAll(table.getOrders());
        }

        if (orders.size() > 0){
            for (Order order : orders) {
                order.setProductsInOrders(new HashSet<>());
            }
            orderRepository.deleteAll(orders);
        }

        for (CafeTable table : assignedTables) {
            table.setUser(null);
            table.setOrders(new HashSet<>());
        }
        userRepo.delete(user);
    }
}
