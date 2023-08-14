package com.example.capstone1.Controller;
import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
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

        String buy = userService.buyProduct(userId, productId, merchantId);

        if (buy.equals("Product bought successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(buy));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(buy));
        }
    }

    // Ideas for extra credit
    // process of returning the product to the merchant so that the credit is returned to the customer and the product is returned to the  merchant stock
    @PutMapping("/returnProduct/{userId}/{productId}/{merchantId}")
    public ResponseEntity returnProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        String returnProd = userService.returnProduct(userId, productId, merchantId);

        if (returnProd.equals("Product returned successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(returnProd));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(returnProd));
        }
    }
}
