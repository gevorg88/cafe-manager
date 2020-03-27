package org.example.cafemanager.services.user;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.repositories.OrderRepository;
import org.example.cafemanager.repositories.UserRepository;
import org.example.cafemanager.services.communication.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
    void findByEmail() {
        final String username = "a";
        final String email = "val@gug.com";
        User u = new User();
        u.setFirstName("Valodik");
        u.setLastName("Gugushikyan");
        u.setUsername(username);
        u.setEmail(email);
        u.setRole(Role.WAITER);

//        Mockito.when(userRepository
//                .findByEmail(email))
//                .thenReturn(u);

        Assert.assertEquals(userService.findByEmail(email), u);
    }
}
