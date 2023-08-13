package com.example.capstone1.Service;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {


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
                double currentBalance= user.getBalance();
                if ( currentBalance >= amount) {
                    user.setBalance(currentBalance - amount);
                    return true;
                }
                return false;
            }
        }
        return false;
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
