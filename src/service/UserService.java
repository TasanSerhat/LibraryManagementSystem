
package service;

import java.util.List;
import model.User;

public interface UserService {
    
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    
}
