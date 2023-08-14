package com.example.capstone1.Service;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getAllProduct(){
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public boolean updateProduct(String id, Product product){
        for (int i = 0 ; i < products.size();i++){
            if (products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size();i++){
            if (products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public Product getProductId(String productId){
        for (Product product:products){
            if (product.getId().equals(productId)){
                return product;
            }
        }
        return null;
    }
}

