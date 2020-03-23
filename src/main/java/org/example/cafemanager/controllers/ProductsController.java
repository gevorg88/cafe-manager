package org.example.cafemanager.controllers;

import org.example.cafemanager.dto.product.ProductCreate;
import org.example.cafemanager.dto.product.ProductCreateRequestBody;
import org.example.cafemanager.dto.CreateAjaxResponse;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.product.contracts.ProductService;
import org.example.cafemanager.utilities.ValidationMessagesCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @PostMapping
    public ResponseEntity<?> store(
            @Valid @RequestBody ProductCreateRequestBody requestBody,
            Errors errors
    ) {
        CreateAjaxResponse result = new CreateAjaxResponse();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        ProductCreate createDto = new ProductCreate(requestBody.getName());
        try {
            productService.createProduct(createDto);
            result.setMessage("Product has been successfully created");
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody ProductCreateRequestBody requestBody,
            @PathVariable Long id,
            Errors errors
    ) {
        CreateAjaxResponse result = new CreateAjaxResponse();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            productService.update(id, requestBody);
            result.setMessage("Product has been successfully updated");
            return ResponseEntity.ok(result);
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
