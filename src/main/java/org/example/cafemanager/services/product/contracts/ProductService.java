package org.example.cafemanager.services.product.contracts;

import org.example.cafemanager.dto.product.ProductCreate;
import org.example.cafemanager.domain.Product;
import org.example.cafemanager.dto.product.ProductCreateRequestBody;
import org.example.cafemanager.dto.product.SimpleProductProps;

import java.util.Collection;

public interface ProductService {

    Iterable<Product> all();

    Collection<SimpleProductProps> getAllProducts();

    Product createProduct(ProductCreate createDto);

    Product update(Long id, ProductCreateRequestBody requestBody);

    Product findOneById(Long productId);
}
