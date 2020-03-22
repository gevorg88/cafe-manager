package org.example.cafemanager.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateRequest {
    @JsonProperty("products")
    private List<ProductInOrderReq> products = new ArrayList<>();

    public List<ProductInOrderReq> getProducts() {
        return products;
    }

    public void setProduct(List<ProductInOrderReq> products) {
        this.products = products;
    }
}
