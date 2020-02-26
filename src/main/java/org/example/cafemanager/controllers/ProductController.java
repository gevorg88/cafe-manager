package org.example.cafemanager.controllers;

import org.example.cafemanager.dto.product.ProductCreate;
import org.example.cafemanager.domain.Product;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String index(Model model) {
        model.addAttribute("products", productService.all());
        return "product";
    }

    @PostMapping("/products")
    public String store(
            @ModelAttribute("name") String name,
            Model model
    ) {
        Product product = productService.add(new ProductCreate(name));
        model.addAttribute("product", product);
        model.addAttribute("message", "product is successfully added!");
        return "product";
    }
}
