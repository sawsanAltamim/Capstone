package com.example.capstone1.Controller;
import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getAllUser() {
        ArrayList<User> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isUpdateUser = userService.updateUser(id, user);
        if (isUpdateUser) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User update successfully"));
        }
        return ResponseEntity.status(HttpStatus.OK).body("ID not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean isDeleteUser = userService.deleteUser(id);
        if (isDeleteUser) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category delete successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("ID not found"));
    }

    @PutMapping("/buyProduct/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        User user = userService.getUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User not found"));
        }
        Product product = productService.getProductId(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product not found"));
        }
        Merchant merchant = merchantService.getMerchantId(merchantId);
        if (merchant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant not found"));
        }
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productId, merchantId);
        if (merchantStock == null || merchantStock.getStock() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock");
        }
        if(user.getBalance() < product.getPrice()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
        boolean isDeducted = userService.deductBalance(userId, product.getPrice());
        boolean isReduced = merchantStockService.reduceStock(productId, merchantId, 1);

        if (isDeducted && isReduced) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product bought successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("failed"));
    }

}
