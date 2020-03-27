package org.example.cafemanager.repositories;

import org.example.Util;
import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.Order;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.domain.enums.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = PersistenceException.class)
    public void persistOrderWithoutStatus() {
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(5));
        Order order = new Order();
        order.setStatus(null);
        order.setCafeTable(table);
        entityManager.persist(table);
        entityManager.persist(order);
        entityManager.flush();
    }

    @Test(expected = PersistenceException.class)
    public void persistOrderWithoutTable() {
        Order order = new Order();
        entityManager.persist(order);
        entityManager.flush();
    }

    @Test
    public void findOrderByStatus() {
        Order order = new Order();
        order.setStatus(Status.CLOSED);
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(5));
        order.setCafeTable(table);
        entityManager.persist(table);
        entityManager.persist(order);
        entityManager.flush();

        Assert.assertNotNull(orderRepository.findOrderByStatus(Status.CLOSED));
    }
    @Test
    public void getByIdAndCafeTable_User() {
        Order order = new Order();
        order.setStatus(Status.CLOSED);

        CafeTable table = new CafeTable();
        table.setName(Util.randomString(5));

        User user = new User();
        user.setFirstName(Util.randomString(6));
        user.setLastName(Util.randomString(6));
        user.setUsername(Util.randomString(6));
        user.setPassword(Util.randomString(6));
        user.setEmail("test@test.test");
        user.setRole(Role.WAITER);

        table.setUser(user);
        order.setCafeTable(table);

        entityManager.persist(user);
        entityManager.persist(table);
        entityManager.persist(order);
        entityManager.flush();

        Assert.assertNotNull(orderRepository.getByIdAndCafeTable_User(order.getId(), user));
    }
}
