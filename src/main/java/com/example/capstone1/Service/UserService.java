package com.example.capstone1.Service;
import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;

    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getAllUser() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean deductBalance(String userId, double amount) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                double currentBalance = user.getBalance();
                if (currentBalance >= amount) {
                    user.setBalance(currentBalance - amount);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean returnAmountToBalance(String userId, double amount) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                double currentBalance = user.getBalance();
                    user.setBalance(currentBalance + amount);
                    return true;
                }
                return false;
            }
        return false;
    }

    public String buyProduct(String userId, String productId, String merchantId) {
        User user = this.getUserId(userId);
        Product product = productService.getProductId(productId);
        Merchant merchant = merchantService.getMerchantId(merchantId);
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productId, merchantId);

        if (user == null) {
            return ("User not found");
        } else if (product == null) {
            return ("Product not found");
        } else if (merchant == null) {
            return ("Merchant not found");
        } else if (merchantStock == null || merchantStock.getStock() <= 0) {
            return ("Product out of stock");
        } else if (user.getBalance() < product.getPrice()) {
            return ("Insufficient balance");
        } else {
            boolean isDeducted = deductBalance(userId, product.getPrice());
            boolean isReduced = merchantStockService.reduceStock(productId, merchantId, 1);

            if (isDeducted && isReduced) {
                return ("Product bought successfully");
            }
            return ("Failed to buy product");
        }
    }


    // Ideas for extra credit
    public String returnProduct(String userId, String productId, String merchantId) {
        User user = getUserId(userId);
        Product product = productService.getProductId(productId);
        Merchant merchant = merchantService.getMerchantId(merchantId);

        if (user == null) {
            return ("User not found");
        } else if (product == null) {
            return ("Product not found");
        } else if (merchant == null) {
            return ("Merchant not found");
        } else {
            boolean isReturnProduct = returnAmountToBalance(userId, product.getPrice());
            boolean isStock = merchantStockService.returnToStock(productId, merchantId, 1);

            if (isReturnProduct && isStock) {
                return ("Product returned successfully");
            }
            return ("Failed to returned product");
        }
    }
    public User getUserId(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
