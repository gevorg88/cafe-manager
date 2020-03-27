package org.example.cafemanager.repositories;

import org.example.Util;
import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.table.OnlyTableProps;
import org.example.cafemanager.dto.table.TableWithOpenOrdersCount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CafeTableRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CafeTableRepository cafeTableRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = ConstraintViolationException.class)
    public void persistTableWithoutName() {
        CafeTable table = new CafeTable();

        entityManager.persist(table);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistTableWithVeryLongName() {
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(35));
        entityManager.persist(table);
        entityManager.flush();
    }

    @Test
    public void findAllBy() {
        CafeTable table1 = new CafeTable();
        table1.setName(Util.randomString(5));
        CafeTable table2 = new CafeTable();
        table2.setName(Util.randomString(5));
        entityManager.persist(table1);
        entityManager.persist(table2);
        entityManager.flush();

        Assert.assertEquals(cafeTableRepository.findAllBy().size(), 2);
    }

    @Test
    public void findOneByName() {
        CafeTable table = new CafeTable();
        String name = Util.randomString(5);
        table.setName(name);
        entityManager.persist(table);
        entityManager.flush();

        OnlyTableProps found = cafeTableRepository.findOneByName(name);
        Assert.assertEquals(found.getName(), name);
    }

    @Test
    public void findCafeTableById() {
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(6));
        entityManager.persist(table);
        entityManager.flush();

        CafeTable table1 = cafeTableRepository.findCafeTableById(table.getId());
        Assert.assertEquals(
                table1, table
        );
    }

    @Test
    public void findCafeTableByNameAndIdIsNot() {
        CafeTable table = new CafeTable();
        String name = Util.randomString(6);
        table.setName(name);
        entityManager.persist(table);
        entityManager.flush();

        CafeTable table1 = cafeTableRepository.findCafeTableByNameAndIdIsNot(name, table.getId() + 1);
        Assert.assertEquals(
                table1, table
        );
    }

    @Test
    public void getAllByUserIdIs() {
        CafeTable table = new CafeTable();
        String name = Util.randomString(6);
        table.setName(name);

        User user = new User();
        user.setEmail("test@email.email");
        user.setUsername(Util.randomString(6));
        user.setPassword(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setRole(Role.WAITER);

        table.setUser(user);

        entityManager.persist(table);
        entityManager.persist(user);
        entityManager.flush();

        Assert.assertEquals(cafeTableRepository.getAllByUserIdIs(user.getId()).size(), 1);
    }

    @Test
    public void userTablesWithOrders() {
        CafeTable table = new CafeTable();
        String name = Util.randomString(6);
        table.setName(name);

        User user = new User();
        user.setEmail("test@email.email");
        user.setUsername(Util.randomString(6));
        user.setPassword(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setRole(Role.WAITER);

        table.setUser(user);

        entityManager.persist(table);
        entityManager.persist(user);
        entityManager.flush();

        Optional<TableWithOpenOrdersCount> t = cafeTableRepository
                .userTablesWithOrders(user.getId())
                .stream().findFirst();
              Assert.assertTrue(t.isPresent());
        int count = t.get().getOrderCount();

        Assert.assertEquals(count, 0);
    }

    @Test
    public void findCafeTableByIdAndUser() {
        CafeTable table = new CafeTable();
        String name = Util.randomString(6);
        table.setName(name);

        User user = new User();
        user.setEmail("test@email.email");
        user.setUsername(Util.randomString(6));
        user.setPassword(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setFirstName(Util.randomString(6));
        user.setRole(Role.WAITER);

        table.setUser(user);

        entityManager.persist(table);
        entityManager.persist(user);
        entityManager.flush();

        Assert.assertEquals(table, cafeTableRepository.findCafeTableByIdAndUser(table.getId(), user));
    }
}
