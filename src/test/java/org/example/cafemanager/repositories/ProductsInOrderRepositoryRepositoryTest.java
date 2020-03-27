package org.example.cafemanager.repositories;

import org.example.Util;
import org.example.cafemanager.domain.*;
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
public class ProductsInOrderRepositoryRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProductsInOrderRepository productsInOrderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = PersistenceException.class)
    public void persistProductsInOrderWithoutProduct() {
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(5));

        Order order = new Order();
        order.setCafeTable(table);

        ProductsInOrder pio = new ProductsInOrder();
        pio.setAmount(2);
        pio.setOrder(order);

        entityManager.persist(table);
        entityManager.persist(order);
        entityManager.persist(pio);
        entityManager.flush();
    }

    @Test(expected = PersistenceException.class)
    public void persistProductsInOrderWithoutOrder() {
        Product product = new Product();
        product.setName(Util.randomString(5));

        ProductsInOrder pio = new ProductsInOrder();
        pio.setAmount(2);
        pio.setProduct(product);

        entityManager.persist(product);
        entityManager.persist(pio);
        entityManager.flush();
    }

    @Test
    public void productsInOrderIsCreated() {
        CafeTable table = new CafeTable();
        table.setName(Util.randomString(5));

        Order order = new Order();
        order.setCafeTable(table);

        Product product = new Product();
        product.setName(Util.randomString(5));

        ProductsInOrder pio = new ProductsInOrder();
        pio.setOrder(order);
        pio.setAmount(2);
        pio.setProduct(product);

        entityManager.persist(table);
        entityManager.persist(order);
        entityManager.persist(product);
        entityManager.persist(pio);
        entityManager.flush();

        Assert.assertNotNull(productsInOrderRepository.findById(pio.getId()));
    }
}
