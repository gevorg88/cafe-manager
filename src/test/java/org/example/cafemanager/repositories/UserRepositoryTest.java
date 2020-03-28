package org.example.cafemanager.repositories;

import org.example.utils.Util;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutPassword() {
        User u = createUser();
        u.setPassword(null);
        entityManager.persist(u);
        entityManager.flush();

        entityManager.clear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutEmail() {
        User u = createUser();
        u.setEmail(null);
        entityManager.persist(u);
        entityManager.flush();

        entityManager.clear();
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistUserWithoutUsername() {
        User u = createUser();
        u.setUsername(null);
        entityManager.persist(u);
        entityManager.flush();

        entityManager.clear();
    }

    @Test(expected = PersistenceException.class)
    public void persistUserWithDupEmail() {
        User u1 = createUser();
        entityManager.persist(u1);
        entityManager.flush();

        User u2 = createUser();
        entityManager.persist(u2);
        entityManager.flush();

        entityManager.clear();
    }

    @Test(expected = PersistenceException.class)
    public void persistUserWithDupUsername() {
        String username = Util.randomString(6);
        User u1 = createUser(username);
        entityManager.persist(u1);
        entityManager.flush();

        User u2 = createUser(username);
        u2.setEmail("r" + this.email);
        entityManager.persist(u2);
        entityManager.flush();

        entityManager.clear();
    }

    @Test
    public void findByUsername() {
        String username = Util.randomString(6);
        User u = createUser(username);
        entityManager.persist(u);
        entityManager.flush();

        User found = userRepository.findUserByUsernameOrEmail(username, "email");
        Assert.assertEquals(found.getUsername(), username);

        entityManager.clear();
    }

    @Test
    public void findByEmail() {
        User u = createUser();
        entityManager.persist(u);
        entityManager.flush();

        User found = userRepository.findUserByEmail(this.email);
        Assert.assertEquals(found.getEmail(), this.email);

        entityManager.clear();
    }

    @Test
    public void findAllByRole() {
        User u = createUser();
        entityManager.persist(u);
        entityManager.flush();

        userRepository.findAllByRole(Role.WAITER);
        Assert.assertEquals(
                userRepository.findAllByRole(Role.WAITER).size(), 1
        );

        entityManager.clear();
    }

    @Test
    public void findById() {
        User u = createUser();
        entityManager.persist(u);
        entityManager.flush();

        User user = userRepository.findUserById(u.getId());
        Assert.assertEquals(
                user, u
        );

        entityManager.clear();
    }
}
