package org.example.cafemanager.services.user;

import org.example.cafemanager.EntitiesBuilder;
import org.example.cafemanager.Util;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.dto.user.UpdateUserRequestBody;
import org.example.cafemanager.dto.user.UserCreateRequestBody;
import org.example.cafemanager.dto.user.UserPublicProfile;
import org.example.cafemanager.repositories.OrderRepository;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.communication.NotificationService;
import org.example.cafemanager.services.communication.impls.EmailService;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.impls.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private EmailService notificationService;

    @Test
    public void findByEmail() {
        User u = EntitiesBuilder.createUser();

        Mockito.when(userRepository.findUserByEmail(EntitiesBuilder.email)).thenReturn(u);
        Assert.assertEquals(userService.findByEmail(EntitiesBuilder.email), u);
    }

    @Test
    public void findByUsernameOrEmail() {
        String username = Util.randomString(6);
        User u = EntitiesBuilder.createUser(username);

        Mockito
                .when(userRepository.findUserByUsernameOrEmail(username, EntitiesBuilder.email))
                .thenReturn(u);
        Assert.assertEquals(userService.findByUsernameOrEmail(username, EntitiesBuilder.email), u);
    }

    @Test
    public void findAllUsers() {
        List<User> usersSet = Arrays.asList(EntitiesBuilder.createUser(), EntitiesBuilder.createUser());

        Mockito
                .when(userRepository.findAll())
                .thenReturn(usersSet);
        int size = 0;
        Iterator<User> usersIterator = userService.findAllUsers().iterator();
        for (; usersIterator.hasNext(); usersIterator.next()) {
            size++;
        }

        Assert.assertEquals(usersSet.size(), size);
    }

    @Test
    //TODO discuss
    public void create() {
        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();

        User u = EntitiesBuilder.createUser();
        u.setUsername(uq.getUsername());

        Mockito
                .when(userService.create(uq, Role.WAITER))
                .thenReturn(u);
        Assert.assertEquals(uq.getUsername(), u.getUsername());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullableRequest() {
        User u = EntitiesBuilder.createUser();
        Mockito
                .when(userService.create(null, Role.WAITER))
                .thenReturn(u);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullableStatus() {
        User u = EntitiesBuilder.createUser();
        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
        Mockito
                .when(userService.create(uq, null))
                .thenReturn(u);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullableEmail() {
        User u = EntitiesBuilder.createUser();
        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
        uq.setEmail(null);
        Mockito
                .when(userService.create(uq, null))
                .thenReturn(u);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullableUsername() {
        User u = EntitiesBuilder.createUser();
        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
        uq.setUsername(null);
        Mockito
                .when(userService.create(uq, null))
                .thenReturn(u);
    }

    @Test
    public void createWithDuplicateUsername() {
        User u = EntitiesBuilder.createUser();
        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();

        Mockito
                .when(userRepository.save(u))
                .thenReturn(u);
        uq.setUsername(u.getUsername());
        Assert.assertThrows(MustBeUniqueException.class, () -> {
            userService.create(uq, Role.WAITER);
        });
    }

    @Test
    public void save() {
        User u = EntitiesBuilder.createUser();
        Mockito.when(userRepository.save(u)).thenReturn(u);
        Assert.assertEquals(userService.save(u), u);
    }

    @Test
    public void getAllWaiters() {
        List<UserPublicProfile> users = Arrays.asList(
                EntitiesBuilder.createUserPublicProfile(1L),
                EntitiesBuilder.createUserPublicProfile(2L)
        );
        Mockito.when(userRepository.findAllByRole(Role.WAITER)).thenReturn(users);
        Assert.assertEquals(userService.getAllWaiters().size(), users.size());
    }

    @Test
    public void getUserById() {
        Long id = 1L;
        User user = EntitiesBuilder.createUser();
        user.setId(id);
        Mockito.when(userRepository.findUserById(id))
                .thenReturn(user);
        Assert.assertEquals(user, userService.getUserById(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNullUpdateUserRequestBody() {
        User u = EntitiesBuilder.createUser();
        Mockito
                .when(userService.update(1L, null))
                .thenReturn(u);
    }

    @Test
    public void updateWithNotFoundException() {
        User u = EntitiesBuilder.createUser();
        u.setId(1L);
        Mockito
                .when(userRepository.findUserById(u.getId()))
                .thenReturn(u);
        Assert.assertThrows(InstanceNotFoundException.class, () -> {
            userService.update(2L, new UpdateUserRequestBody());
        });
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}
