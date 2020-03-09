package org.example.cafemanager.dto.product;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;

@Component
public class ProductCreateRequestBody {
    @NotBlank(message = "Product name is required")
    @Length(max = 32, min = 6, message = "Product name must contain at from 6 to 32 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
