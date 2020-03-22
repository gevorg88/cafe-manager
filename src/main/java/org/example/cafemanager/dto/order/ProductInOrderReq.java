package org.example.cafemanager.dto.order;

import java.io.Serializable;

public class ProductInOrderReq implements Serializable {
    private Integer amount = 1;

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
