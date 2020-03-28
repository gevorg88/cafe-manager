package org.example.cafemanager.repositories;

import org.example.utils.Util;
import org.example.cafemanager.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;

public class ProductsInOrderRepositoryRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ProductsInOrderRepository productsInOrderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = PersistenceException.class)
    public void persistProductsInOrderWithoutProduct() {
        ProductsInOrder pio = createProductInOrder();
        pio.setProduct(null);
        entityManager.persist(pio.getOrder().getCafeTable());
        entityManager.persist(pio.getOrder());
        entityManager.persist(pio);
        entityManager.flush();

        entityManager.clear();
    }

    @Test(expected = PersistenceException.class)
    public void persistProductsInOrderWithoutOrder() {
        ProductsInOrder pio = createProductInOrder();
        pio.setOrder(null);

        entityManager.persist(pio.getProduct());
        entityManager.persist(pio);
        entityManager.flush();

        entityManager.clear();
    }

    @Test
    public void productsInOrderIsCreated() {
        ProductsInOrder pio = createProductInOrder();

        entityManager.persist(pio.getOrder().getCafeTable());
        entityManager.persist(pio.getOrder());
        entityManager.persist(pio.getProduct());
        entityManager.persist(pio);
        entityManager.flush();
        entityManager.clear();

        Assert.assertNotNull(productsInOrderRepository.findById(pio.getId()));
    }
}
