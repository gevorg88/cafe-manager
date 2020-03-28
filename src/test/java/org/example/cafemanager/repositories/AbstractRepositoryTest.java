package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.*;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.domain.enums.Status;
import org.example.utils.Util;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
abstract class AbstractRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    final String email = "test@test.test";

    User createUser() {
        User u = new User();
        u.setEmail(email);
        u.setUsername(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setRole(Role.WAITER);
        return u;
    }

    User createUser(String username) {
        User u = createUser();
        u.setUsername(username);
        return u;
    }

    CafeTable createCafeTable(String tableName) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(tableName);
        return cafeTable;
    }

    CafeTable createCafeTable() {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(Util.randomString(6));
        return cafeTable;
    }

    CafeTable createCafeTable(User user) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(Util.randomString(6));
        cafeTable.setUser(user);
        return cafeTable;
    }

    Order createOrder() {
        Order o = new Order();
        o.setCafeTable(createCafeTable());
        o.setStatus(Status.OPEN);
        return o;
    }

    Product createProduct(String productName) {
        Product product = new Product();
        product.setName(productName);
        return product;
    }

    Product createProduct() {
        Product product = new Product();
        product.setName(Util.randomString(6));
        return product;
    }

    ProductsInOrder createProductInOrder() {
        ProductsInOrder p = new ProductsInOrder();
        Order o = createOrder();
        o.setCafeTable(createCafeTable());
        p.setOrder(o);
        p.setProduct(createProduct());
        p.setAmount(1);

        return p;
    }
}
