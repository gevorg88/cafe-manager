package org.example.cafemanager.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class ProductInOrderReq implements Serializable {
    @JsonProperty("amount")
    private Integer amount = 1;

    @JsonProperty("productId")
    private Long productId;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
