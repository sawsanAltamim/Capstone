package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @GetMapping("/get")
    public ResponseEntity getAllMerchants (){
        ArrayList<Merchant> merchants = merchantService.getAllMerchants();
        return ResponseEntity.status(HttpStatus.OK).body(merchants);
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant added successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id , @RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isUpdateMarchant = merchantService.updateMerchant(id,merchant);
        if(isUpdateMarchant){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant update successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("ID not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id){
        boolean isDeleteMerchant = merchantService.deleteMerchant(id);
        if (isDeleteMerchant){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Marchant delete successfully"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ID not found"));
    }
}
