
package travel.and.tourism.management.system;


public class UserSession {
    private static UserSession instance;
    private String loggedInUsername;
    private String role;

    private UserSession() {
        // Khởi tạo UserSession
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
     public boolean isLoggedIn() {
        return loggedInUsername != null; // Trả về true nếu đã có người dùng đăng nhập, ngược lại trả về false
    }
}

