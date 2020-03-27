package org.example.cafemanager.repositories;

import org.example.Util;
import org.example.cafemanager.domain.Product;
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
public class ProductRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test(expected = ConstraintViolationException.class)
    public void persistProductWithoutName() {
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();
    }

    @Test(expected = PersistenceException.class)
    public void persistOrderWithDupName() {
        String name = Util.randomString(6);
        Product product1 = new Product();
        product1.setName(name);

        Product product2 = new Product();
        product2.setName(name);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();
    }

    @Test
    public void findAllBy() {
        Product product = new Product();
        product.setName(Util.randomString(6));

        entityManager.persist(product);
        entityManager.flush();

        Assert.assertEquals(productRepository.findAllBy().size(), 1);
    }

    @Test
    public void findProductByName() {
        String name = Util.randomString(6);
        Product product = new Product();
        product.setName(name);

        entityManager.persist(product);
        entityManager.flush();

        Assert.assertNotNull(productRepository.findProductByName(name));
    }

    @Test
    public void findProductById() {
        Product product = new Product();
        product.setName(Util.randomString(6));

        entityManager.persist(product);
        entityManager.flush();

        Assert.assertNotNull(productRepository.findProductById(product.getId()));
    }

    @Test
    public void findProductByNameAndIdIsNot() {
        String name = Util.randomString(6);
        Product product = new Product();
        product.setName(name);

        entityManager.persist(product);
        entityManager.flush();

        Assert.assertNotNull(productRepository.findProductByNameAndIdIsNot(name, product.getId() + 1));
    }
}
