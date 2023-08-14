package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getAllMerchantStocks(){
        return merchantStocks;
    }

    public void addMerchantStock(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }
    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addStock(String productId, String merchantId, Integer amount) {
        for (MerchantStock merchantStock: merchantStocks){
            if (merchantStock.getProductID().equals(productId) && merchantStock.getMerchantID().equals(merchantId)){
                int currentStock = merchantStock.getStock();
                merchantStock.setStock(currentStock + amount);
                return true;
            }
        }
        return false;
    }

    public boolean reduceStock(String productId, String merchantId, Integer amount){
        for (MerchantStock merchantStock: merchantStocks){
            if(merchantStock.getMerchantID().equals(merchantId) && merchantStock.getProductID().equals(productId)){
                int currentStock = merchantStock.getStock();
                if(currentStock >= amount){
                    merchantStock.setStock( currentStock - amount );
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean returnToStock(String productId, String merchantId, Integer amount){
        for (MerchantStock merchantStock: merchantStocks){
            if(merchantStock.getMerchantID().equals(merchantId) && merchantStock.getProductID().equals(productId)){
                int currentStock = merchantStock.getStock();
                    merchantStock.setStock( currentStock + amount );
                    return true;
                }
                return false;
            }
        return false;
    }
    public MerchantStock getMerchantStock(String productId, String merchantId){
        for (MerchantStock merchantStock: merchantStocks){
            if (merchantStock.getMerchantID().equals(merchantId) && merchantStock.getProductID().equals(productId)){
                return merchantStock;
            }
        }
        return null;
    }
}
