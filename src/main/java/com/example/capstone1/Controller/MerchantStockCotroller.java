package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/MerchantStock")
@RequiredArgsConstructor
public class MerchantStockCotroller {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getAllMerchantStocks (){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getAllMerchantStocks();
        return ResponseEntity.status(HttpStatus.OK).body(merchantStocks);
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock (@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant stock added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isUpdateMerchantStock = merchantStockService.updateMerchantStock(id, merchantStock);
        if(isUpdateMerchantStock){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant stock update successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id){
        boolean isDeleteMerchantStock = merchantStockService.deleteMerchantStock(id);
        if(isDeleteMerchantStock){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant stock delete successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("ID not found"));
    }

    @PostMapping("/addStock/{productId}/{merchantId}/{amount}")
    public ResponseEntity addStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable Integer amount){
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Additional stock must be greater than 0"));
        }
        boolean isAddStock = merchantStockService.addStock(productId, merchantId, amount);
        if(isAddStock){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Stock added to merchant successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product or merchant not found"));
    }
}
