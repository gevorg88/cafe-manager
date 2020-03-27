package org.example.cafemanager.services.product.contracts;

import org.example.cafemanager.dto.product.ProductCreate;
import org.example.cafemanager.domain.Product;
import org.example.cafemanager.dto.product.CreateProductRequest;
import org.example.cafemanager.dto.product.SimpleProductProps;

import java.util.Collection;

public interface ProductService {

    Iterable<Product> all();

    Collection<SimpleProductProps> getAllProducts();

    Product createProduct(ProductCreate createDto);

    Product update(Long id, CreateProductRequest requestBody);

    Product findOneById(Long productId);

    void destroy(Long productId);
}
