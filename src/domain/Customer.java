package domain;
import java.io.Serializable;

//Represents a store customer. Stores the customer name, ID and activity state. 
//A customer is considered active when they have an ongoing rental

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private String customerName;
    private String customerId;
    private boolean customerState = false; // Initialized as inactive (no active rental)

    // Constuctor
    public Customer(String customerName, String customerId) {

        this.customerName = customerName;
        this.customerId = customerId;

    }
    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean getCustomerState() {
        return customerState;

    }

    public void changeCustomerState() { // Toggles the customer's active status

        if (customerState == false) {
            customerState = true;
        }
        else {
            customerState = false;
        }
    }
}
