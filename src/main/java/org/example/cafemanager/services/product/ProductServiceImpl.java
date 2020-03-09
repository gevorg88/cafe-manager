package org.example.cafemanager.services.product;

import org.example.cafemanager.domain.CafeTable;
import org.example.cafemanager.dto.product.ProductCreate;
import org.example.cafemanager.domain.Product;
import org.example.cafemanager.dto.product.ProductCreateRequestBody;
import org.example.cafemanager.dto.product.SimpleProductProps;
import org.example.cafemanager.repositories.ProductRepository;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductCreate productCreate) {
        SimpleProductProps existingProd = productRepository.findProductByName(productCreate.getName());
        if (null != existingProd) {
            throw new MustBeUniqueException("product");
        }
        Product product = new Product();
        product.setName(productCreate.getName());
        productRepository.save(product);
        return product;
    }

    @Override
    public Iterable<Product> all() {
        return productRepository.findAll();
    }

    @Override
    public Collection<SimpleProductProps> getAllProducts() {
        return productRepository.findAllBy();
    }

    @Override
    public Product update(Long id, ProductCreateRequestBody requestBody) {
        Product product = productRepository.findProductById(id);
        if (null == product) {
            throw new InstanceNotFoundException("product");
        }

        Product existingProduct = productRepository.findProductByNameAndIdIsNot(requestBody.getName(), id);
        if (null != existingProduct) {
            throw new MustBeUniqueException("name");
        }

        if (!product.getName().equals(requestBody.getName())) {
            product.setName(requestBody.getName());
            productRepository.save(product);
        }

        return product;
    }
}
