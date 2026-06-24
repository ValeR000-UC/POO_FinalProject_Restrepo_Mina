package domain;
import java.io.Serializable;
import java.util.ArrayList;

//Represents a rental transaction. Links a customer and a user to a list of rented movies. 
//Handles rental cost calculation, movie status changes and customer state updates.

public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private ArrayList<Movie> rentedMovies = new ArrayList<>();
    private User user;
    private Customer customer;
    private String rentalId;
    private boolean rentalActive = true; // Created as active by default
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

    private void calculateRentalCost(int currentPrice) { // Method that calculates the rental cost
        
        rentalCost = (rentalDays * currentPrice) * consultRentedMovies().size();
        
    }

    public void processRentMovie(int currentPrice, ArrayList<Movie> movies) {

        calculateRentalCost(currentPrice); // Calculate the rental cost of the movies
        rent(movies); // Perform the rental process
    
    }

    public void processReturnMovie(ArrayList<Movie> movies) {
        returnMovie(movies); 
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
        return user; // Return user associated with the rental
    }

    public Customer getCustomer() {
        return customer; // // Return customer associated with the rental
    }

    public ArrayList<Movie> consultRentedMovies() {
        return rentedMovies;
    }

    // Setters
    private void rent(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
            movie.markAsRented(); // // Change movie status to rented
        }
        customer.changeCustomerState(); // Customer active (cannot rent)
    }

    private void returnMovie(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
            movie.markAsAvaible(); // Change movie status to available (returned)
        }

        customer.changeCustomerState(); // Customer inactive (can rent)
        setRentalState(false);}


    public void setRentalState(boolean state) {
        rentalActive = state; // Restore state from CSV
    }

    public void setRentalCost(int cost) {
        rentalCost = cost; // Restore fixed cost from CSV, do not recalculate
    }
}
