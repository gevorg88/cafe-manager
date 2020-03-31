package org.example.cafemanager.services;

import org.example.cafemanager.EntitiesBuilder;
import org.example.cafemanager.Util;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.dto.user.UpdateUserRequestBody;
import org.example.cafemanager.dto.user.UserPublicProfile;
import org.example.cafemanager.repositories.CafeTableRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.table.impls.TableServiceImpl;
import org.example.cafemanager.services.user.impls.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {

    @InjectMocks
    private TableServiceImpl tableService;

    @Mock
    private CafeTableRepository cafeTableRepository;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void getAllTables() {
        List<SimpleTableProps> tables = Arrays.asList(
                EntitiesBuilder.createSimpleTableProps(1L),
                EntitiesBuilder.createSimpleTableProps(2L)
        );
        Mockito.when(cafeTableRepository.findAllBy()).thenReturn(tables);
        Assert.assertEquals(tableService.getAllTables().size(), tables.size());
    }

    @Test()
    public void createWithNullableRequest() {
        Assert.assertThrows(IllegalArgumentException.class, ()->tableService.create(null));
    }

//    @Test
//    public void findByUsernameOrEmail() {
//        String username = Util.randomString(6);
//        User u = EntitiesBuilder.createUser(username);
//
//        Mockito
//                .when(userRepository.findUserByUsernameOrEmail(username, EntitiesBuilder.email))
//                .thenReturn(u);
//        Assert.assertEquals(userService.findByUsernameOrEmail(username, EntitiesBuilder.email), u);
//    }
//
//    @Test
//    public void findAllUsers() {
//        List<User> usersSet = Arrays.asList(EntitiesBuilder.createUser(), EntitiesBuilder.createUser());
//
//        Mockito
//                .when(userRepository.findAll())
//                .thenReturn(usersSet);
//        int size = 0;
//        Iterator<User> usersIterator = userService.findAllUsers().iterator();
//        for (; usersIterator.hasNext(); usersIterator.next()) {
//            size++;
//        }
//
//        Assert.assertEquals(usersSet.size(), size);
//    }
//
//    @Test
//    public void create() {
//        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
//
//        User u = EntitiesBuilder.createUser();
//        u.setUsername(uq.getUsername());
//        User user = userService.create(uq, Role.WAITER);
//
//        Assert.assertNotNull(user);
//        Assert.assertEquals(user.getUsername(), uq.getUsername());
//        Mockito.verify(notificationService, Mockito.times(1)).notify(uq);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void createWithNullableRequest() {
//        User u = EntitiesBuilder.createUser();
//        Mockito
//                .when(userService.create(null, Role.WAITER))
//                .thenReturn(u);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void createWithNullableStatus() {
//        User u = EntitiesBuilder.createUser();
//        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
//        Mockito
//                .when(userService.create(uq, null))
//                .thenReturn(u);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void createWithNullableEmail() {
//        User u = EntitiesBuilder.createUser();
//        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
//        uq.setEmail(null);
//        Mockito
//                .when(userService.create(uq, null))
//                .thenReturn(u);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void createWithNullableUsername() {
//        User u = EntitiesBuilder.createUser();
//        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
//        uq.setUsername(null);
//        Mockito
//                .when(userService.create(uq, null))
//                .thenReturn(u);
//    }
//
//    @Test
//    //TODO discuss
//    public void createWithDuplicateUsername() {
//        User u = EntitiesBuilder.createUser();
//        CreateUserRequest uq = EntitiesBuilder.createCreateUserRequest();
//        userRepository.save(u);
//        uq.setEmail(u.getEmail() + "asd");
//        Assert.assertThrows(MustBeUniqueException.class, () -> userService.create(uq, Role.WAITER));
//    }
//
//    @Test
//    public void save() {
//        User u = EntitiesBuilder.createUser();
//        Mockito.when(userRepository.save(u)).thenReturn(u);
//        Assert.assertEquals(userService.save(u), u);
//    }
//
//    @Test
//    public void getAllWaiters() {
//        List<UserPublicProfile> users = Arrays.asList(
//                EntitiesBuilder.createUserPublicProfile(1L),
//                EntitiesBuilder.createUserPublicProfile(2L)
//        );
//        Mockito.when(userRepository.findAllByRole(Role.WAITER)).thenReturn(users);
//        Assert.assertEquals(userService.getAllWaiters().size(), users.size());
//    }
//
//    @Test
//    public void getUserById() {
//        Long id = 1L;
//        User user = EntitiesBuilder.createUser();
//        user.setId(id);
//        Mockito.when(userRepository.findUserById(id))
//                .thenReturn(user);
//        Assert.assertEquals(user, userService.getUserById(id));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void updateWithNullUpdateUserRequestBody() {
//        User u = EntitiesBuilder.createUser();
//        Mockito
//                .when(userService.update(1L, null))
//                .thenReturn(u);
//    }
//
//    @Test
//    public void updateWithNotFoundException() {
//        User u = EntitiesBuilder.createUser();
//        u.setId(1L);
//        Mockito
//                .when(userRepository.findUserById(u.getId()))
//                .thenReturn(u);
//        Assert.assertThrows(InstanceNotFoundException.class, () -> {
//            userService.update(2L, new UpdateUserRequestBody());
//        });
//    }
//
//    @Test
//    public void update() {
//        UpdateUserRequestBody u = EntitiesBuilder.createUpdateUserRequestBody();
//        User user = EntitiesBuilder.createUser();
//        user.setFirstName(u.getFirstName());
//        user.setLastName(u.getLastName());
//        user.setId(1L);
//        Mockito
//                .when(userRepository.findUserById(user.getId()))
//                .thenReturn(user);
//        User updatedUser = userService.update(user.getId(), u);
//        Assert.assertEquals(u.getFirstName(), updatedUser.getFirstName());
//        Assert.assertEquals(u.getLastName(), updatedUser.getLastName());
//    }
//
//    @Test
//    public void delete() {
//        User u = EntitiesBuilder.createUser();
//        u.setId(1L);
//        Mockito
//                .when(userRepository.findUserById(u.getId()))
//                .thenReturn(u);
//        Assert.assertTrue(userService.delete(u.getId()));
//    }
//
//    @Test
//    public void deleteWithNotFoundException() {
//        User u = EntitiesBuilder.createUser();
//        u.setId(1L);
//        Mockito
//                .when(userRepository.findUserById(u.getId() + 1))
//                .thenReturn(u);
//        Assert.assertThrows(InstanceNotFoundException.class, () -> {
//            userService.delete(u.getId());
//        });
//    }
}
