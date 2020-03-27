package org.example.cafemanager.services.user;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserUpdateRequestBody;
import org.example.cafemanager.repositories.OrderRepository;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.communication.NotificationService;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.SecurityUtility;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final OrderRepository orderRepository;

    private final NotificationService notificationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, OrderRepository orderRepository, final NotificationService notificationService) {
        this.userRepo = userRepo;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest, Role role) {
        if (findByUsername(createUserRequest.getUsername()) != null) {
            throw new MustBeUniqueException("username");
        }

        if (findByEmail(createUserRequest.getEmail()) != null) {
            throw new MustBeUniqueException("email");
        }

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());

        user.setPassword(
                SecurityUtility
                        .passwordEncoder()
                        .encode(createUserRequest.getPassword())
        );

        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setRole(role);
        save(user);
        notificationService.notify(createUserRequest);
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

        if (orders.size() > 0) {
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
