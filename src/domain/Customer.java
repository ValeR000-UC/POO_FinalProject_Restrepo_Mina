package domain;
import java.io.Serializable;

//Represents a store customer. Stores the customer name, ID and activity state. 
//A customer is considered active when they have an ongoing rental.

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private String customerName;
    private String customerId;
    private boolean customerState = false; // Se crea inactivo (sin una renta) por defecto.

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

    public void changeCustomerState() {

        if (customerState == false) {
            
            customerState = true;
        }

        else {
            customerState = false;
        }
    }
    // Metodo para modificar el customerState. Cuando se renta una pelicula a su nombre, debe cambiarse a true.

}
