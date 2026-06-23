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
    private int rentalCost;

    // Constructor
    public Rental(ArrayList<Movie> rentedMovies, User user, Customer customer, String rentalId, int rentalDays) {

        this.rentedMovies = rentedMovies;
        this.user = user;
        this.customer = customer;
        this.rentalId = rentalId;
        this.rentalDays = rentalDays;

    }

    private void calculateRentalCost(int currentPrice) { // Metodo que calcula el costo de la renta.
        
        rentalCost = (rentalDays * currentPrice) * consultRentedMovies().size();
        
    }

    public void processRentMovie(int currentPrice, ArrayList<Movie> movies) {

        calculateRentalCost(currentPrice); // Calculamos el costo de la renta de las peliculas.
        rent(movies); // Se hace la renta.
    
    }

    public void processReturnMovie(ArrayList<Movie> movies) {

        returnMovie(movies); // Se devuelve la pelicula.
    }

    public String getRentalId() {
        return rentalId;
    }

    public boolean getRentalState() {
        return rentalActive;
    }

    public int getRentalCost() {
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
    private void rent(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
            movie.markAsRented(); // Cambiamos el estado de la pelicula (se renta).
        }

        customer.changeCustomerState(); // Customer activo (No puede rentar).
    }

    private void returnMovie(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
           movie.markAsAvaible(); // Cambiamos el estado de la pelicula (se devuelve)
        }

        customer.changeCustomerState(); // Customer inactivo (Puede rentar).
    }

}
