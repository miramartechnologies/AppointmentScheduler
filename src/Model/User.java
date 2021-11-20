package Model;

public class User {
    private static String userName;
    private int userId;

    private String password;


    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public static String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }



}