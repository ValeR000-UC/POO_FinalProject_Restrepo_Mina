public class User {
    
    private String userName;
    private String userId;
    private boolean userState = false;

    // Constructor
    User(String userName, String userId) {

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

    // Metodo para modificar el userState. Cuando se registra al iniciar el programa, debe cambiarse a true.
}
