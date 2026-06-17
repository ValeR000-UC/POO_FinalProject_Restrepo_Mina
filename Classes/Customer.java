public class Customer {
    
    // Atributes
    private String customerName;
    private String customerId;
    private boolean customerState = false;

    // Constuctor
    Customer(String customerName, String customerId) {

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

    public void chancgeCustomerState() {

        if (customerState == false) {
            
            customerState = true;
        }

        else {
            customerState = false;
        }
    }
    // Metodo para modificar el customerState. Cuando se renta una pelicula a su nombre, debe cambiarse a true.

}
