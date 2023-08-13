package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "ID cannot be null")
    private String id;

    @NotEmpty(message = "Product ID cannot be null")
    private String productID;

    @NotEmpty(message = "Merchant ID cannot be null")
    private String merchantID;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 11, message ="have to be more than 10 at start")
    private Integer stock;
}
