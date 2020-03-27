package org.example.cafemanager.repositories;

import org.example.Util;
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
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final String email = "test@test.test";

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutPassword() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setUsername(Util.randomString(6));
        u.setEmail(this.email);
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutEmail() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setUsername(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutUsername() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setEmail(this.email);
        u.setPassword(Util.randomString(6));
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();
    }

    @Test(expected = PersistenceException.class)
    public void persistUserWithDupEmail() {
        User u1 = new User();
        u1.setFirstName(Util.randomString(6));
        u1.setLastName(Util.randomString(6));
        u1.setUsername(Util.randomString(6));
        u1.setEmail(this.email);
        u1.setPassword(Util.randomString(6));
        u1.setRole(Role.WAITER);
        entityManager.persist(u1);
        entityManager.flush();

        User u2 = new User();
        u2.setFirstName(Util.randomString(6));
        u2.setLastName(Util.randomString(6));
        u2.setUsername(Util.randomString(6));
        u2.setEmail(this.email);
        u2.setPassword(Util.randomString(6));
        u2.setRole(Role.WAITER);
        entityManager.persist(u2);
        entityManager.flush();
    }

    @Test(expected = PersistenceException.class)
    public void persistUserWithDupUsername() {
        String username = Util.randomString(6);
        User u1 = new User();
        u1.setFirstName(Util.randomString(6));
        u1.setLastName(Util.randomString(6));
        u1.setUsername(username);
        u1.setEmail(this.email);
        u1.setPassword(Util.randomString(6));
        u1.setRole(Role.WAITER);
        entityManager.persist(u1);
        entityManager.flush();

        User u2 = new User();
        u2.setFirstName(Util.randomString(6));
        u2.setLastName(Util.randomString(6));
        u2.setEmail("r" + this.email);
        u2.setUsername(username);
        u2.setPassword(Util.randomString(6));
        u2.setRole(Role.WAITER);
        entityManager.persist(u2);
        entityManager.flush();
    }

    @Test
    public void findByUsername() {
        String username = Util.randomString(6);
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setUsername(username);
        u.setEmail(this.email);
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();

        User found = userRepository.findUserByUsername(username);
        Assert.assertEquals(found.getUsername(), username);
    }

    @Test
    public void findByEmail() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setUsername(Util.randomString(6));
        u.setEmail(this.email);
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();

        User found = userRepository.findUserByEmail(this.email);
        Assert.assertEquals(found.getEmail(), this.email);
    }

    @Test
    public void findAllByRole() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setUsername(Util.randomString(6));
        u.setEmail(this.email);
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();

        userRepository.findAllByRole(Role.WAITER);
        Assert.assertEquals(
                userRepository.findAllByRole(Role.WAITER).size(), 1
        );
    }

    @Test
    public void findById() {
        User u = new User();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setUsername(Util.randomString(6));
        u.setEmail(this.email);
        u.setRole(Role.WAITER);
        entityManager.persist(u);
        entityManager.flush();

        User user = userRepository.findUserById(u.getId());
        Assert.assertEquals(
                user, u
        );
    }
}
