package domain;
import java.io.Serializable;

//Represents the system operator. Stores the username, ID and active state. 
//Associated with rentals to register who processed each transaction.
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userName;
    private String userId;
    private boolean userState = true;

    // Constructor
    public User(String userName, String userId, boolean userState) { //explicar pq puse todos los constructores public

        this.userName = userName;
        this.userId = userId;
        this.userState = userState;

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
