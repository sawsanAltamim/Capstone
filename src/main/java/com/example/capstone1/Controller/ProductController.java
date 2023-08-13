package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getAllProduct (){
        ArrayList<Product> products = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct (@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            String messgae = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messgae);
        }
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse ("Product added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse (message));
        }
        boolean isUpdateProduct= productService.updateProduct(id,product);
        if (isUpdateProduct){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product update successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        boolean isDeleteProduct= productService.deleteProduct(id);
        if (isDeleteProduct){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product delete successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product not found"));
    }
}
