public class Customer {
    
    // Atributes
    private String customerName;
    private String customerId;
    private boolean customerstate;

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

        return customerstate;

    }


    // Metodo para modificar el customerState. Cuando se registra al iniciar el programa, debe cambiarse a true.

}
