package controller;

import dao.authDAO;
import model.User;

public class authController {
    private authDAO authDao;
    
    public authController() {
        this.authDao = new authDAO();
    }
    
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }
        
        return authDao.authenticate(username, password);
    }
    
    public User getUserById(int userId) {
        return authDao.getUserById(userId);
    }
}
