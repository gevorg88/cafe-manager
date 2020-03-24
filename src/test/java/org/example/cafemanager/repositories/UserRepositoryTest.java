package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test(expected= ConstraintViolationException.class)
    public void persistUserWithoutPassword() {
        final String username = "a";
        User u = new User();
        u.setFirstName("Valodik");
        u.setLastName("Gugushikyan");
        u.setUsername(username);
        u.setEmail("val@gug.com");
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();
    }

    @Test(expected= ConstraintViolationException.class)
    public void persistUserWithoutEmail() {
        final String username = "a";
        User u = new User();
        u.setFirstName("Valodik");
        u.setLastName("Gugushikyan");
        u.setUsername(username);
        u.setPassword("password");
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();
    }

    @Test
    public void findByUsername() {
        final String username = "username";
        User u = new User();
        u.setFirstName("Valodik");
        u.setLastName("Gugushikyan");
        u.setUsername(username);
        u.setEmail("val@gug.com");
        u.setPassword("password");
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();

        User found = userRepository.findByUsername(username);
        Assert.assertEquals(found.getUsername(),username);
    }
}
