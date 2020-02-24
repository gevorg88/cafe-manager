package org.example.cafemanager.services.product.contracts;

import org.example.cafemanager.accessData.product.ProductCreate;
import org.example.cafemanager.domain.Product;

public interface ProductService {
    Product add(ProductCreate productCreate);

    Iterable<Product> all();
}
