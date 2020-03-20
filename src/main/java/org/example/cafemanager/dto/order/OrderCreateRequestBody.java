package org.example.cafemanager.dto.order;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
public class OrderCreateRequestBody {
    @NotBlank(message = "Product name is required")
    @Length(max = 32, min = 3, message = "Product name must contain at from 3 to 32 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
