
package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.util.List;
import model.User;

public class UserServiceImpl implements UserService{
    
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void addUser(User user) {
        validateUser(user, false);
        if (userDAO.getUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten alınmış!");
        }
        try {
            userDAO.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı eklenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        validateUser(user, true);
        User existing = userDAO.getUserById(user.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Güncellenecek kullanıcı bulunamadı!");
        }
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) {
        User existing = userDAO.getUserById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Silinecek kullanıcı bulunamadı!");
        }
        try {
            userDAO.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı silinirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı!");
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı!");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    private void validateUser(User user, boolean isUpdate) {
        if (isUpdate && user.getId() <= 0) {
            throw new IllegalArgumentException("Kullanıcı ID geçersiz!");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı boş olamaz!");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Şifre en az 6 karakter olmalı!");
        }
        if (user.getRole() == null || 
            !(user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("member"))) {
            throw new IllegalArgumentException("Rol sadece 'admin' veya 'member' olabilir!");
        }
        // Gerekirse başka validasyonlar eklenebilir
    }

}
