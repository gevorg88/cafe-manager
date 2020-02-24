package org.example.cafemanager.services.product;

import org.example.cafemanager.accessData.product.ProductCreate;
import org.example.cafemanager.domain.Product;
import org.example.cafemanager.repositories.ProductRepository;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product add(ProductCreate productCreate) {
        Product product = new Product();
        product.setName(productCreate.getName());
        this.productRepository.save(product);
        return product;
    }

    @Override
    public Iterable<Product> all() {
        return productRepository.findAll();
    }
}
