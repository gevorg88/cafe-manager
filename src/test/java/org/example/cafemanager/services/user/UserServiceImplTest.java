package org.example.cafemanager.services.user;

import org.example.Util;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    private final String email = "test@test.test";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotificationService notificationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByEmail() {
        String username = Util.randomString(6);
        User u = new User();
        u.setFirstName(username);
        u.setLastName(Util.randomString(6));
        u.setUsername(username);
        u.setEmail(email);
        u.setRole(Role.WAITER);

        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(u);
        Assert.assertEquals(userService.findByEmail(email), u);
    }
}
