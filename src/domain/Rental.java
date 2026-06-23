package domain;
import java.io.Serializable;
import java.util.ArrayList;

public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private ArrayList<Movie> rentedMovies = new ArrayList<>();
    private User user;
    private Customer customer;
    private String rentalId;
    private boolean rentalActive = true; // Se crea en activa por defecto.
    private int rentalDays;
    private float rentalCost;

    // Constructor
    public Rental(ArrayList<Movie> rentedMovies, User user, Customer customer, String rentalId, int rentalDays) {

        this.rentedMovies = rentedMovies;
        this.user = user;
        this.customer = customer;
        this.rentalId = rentalId;
        this.rentalDays = rentalDays;

    }

    private void calculateRentalCost(float currentPrice) { // Metodo que calcula el costo de la renta.
        
        rentalCost = (rentalDays * currentPrice) * consultRentedMovies().size();
        
    }

    public ArrayList<Boolean> processRentMovie(float currentPrice, ArrayList<Movie> movies) {

        calculateRentalCost(currentPrice); // Calculamos el costo de la renta de las peliculas.
        ArrayList<Boolean> activeRentals = rent(movies); // Guardamos los estados de las peliculas.
        return activeRentals; // Se retornan los estados de las peliculas. 

    }

    public ArrayList<Boolean> processReturnMovie(ArrayList<Movie> movies) {

        ArrayList<Boolean> inactiveRentals = returnMovie(movies); // Guardamos los estados de las peliculas.
        return inactiveRentals; // Se retornan los estados de las peliculas.
    }

    public String getRentalId() {
        return rentalId;
    }

    public boolean getRentalState() {
        return rentalActive;
    }

    public float getRentalCost() {
        return rentalCost;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public User getUser() {
        return user; // Retorna usuario asociado a la renta
    }

    public Customer getCustomer() {
        return customer; // Retorna customer asociado a la renta.
    }

    public ArrayList<Movie> consultRentedMovies() {
        return rentedMovies;
    }

    // Setters
    private ArrayList<Boolean> rent(ArrayList<Movie> movies) {

        ArrayList<Boolean> confirmation = new ArrayList<>(); // Aqui guardamos si se renta o no la pelicula.
        for(Movie movie : movies) {
            boolean c = movie.markAsRented(); // Cambiamos el estado de la pelicula (se renta).
            confirmation.add(c); // Se guarda el estado de la renta (si se pudo rentar o no) para imprimir en el main.
        }

        customer.changeCustomerState(); // Customer activo.
        return confirmation; // Retornamos la lista de estados.
    }

    private ArrayList<Boolean> returnMovie(ArrayList<Movie> movies) {

        ArrayList<Boolean> confirmation = new ArrayList<>();
        for(Movie movie : movies) {
           boolean c = movie.markAsAvaible(); // Cambiamos el estado de la pelicula (se devuelve).
           confirmation.add(c); 
        }

        customer.changeCustomerState(); // Customer inactivo.
        return confirmation;
    }

}
