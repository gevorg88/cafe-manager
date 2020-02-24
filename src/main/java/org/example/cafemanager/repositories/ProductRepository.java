package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByName(String name);
}
