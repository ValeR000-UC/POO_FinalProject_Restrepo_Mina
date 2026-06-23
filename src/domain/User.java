package domain;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userName;
    private String userId;
    private boolean userState = true;

    // Constructor
    public User(String userName, String userId) {

        this.userName = userName;
        this.userId = userId;

    }

    // Getters
    public String getUserName() {

        return userName;

    }

    public String getUserid() {

        return userId;

    }

    public boolean getUserState() {

        return userState;
    }

}
