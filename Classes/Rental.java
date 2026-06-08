import java.util.ArrayList;

public class Rental {
    
    // Atributes
    private ArrayList<Movie> rentedMovies = new ArrayList<>();
    private User user;
    private Customer customer;
    private String rentalId;
    private boolean rentalActive = true;
    private int rentalDays;
    private float rentalCost;

    // Constructor
    Rental(ArrayList<Movie> rentedMovies, User user, Customer customer, String rentalId, int rentalDays, float rentalCost) {

        this.rentedMovies = rentedMovies;
        this.user = user;
        this.customer = customer;
        this.rentalId = rentalId;
        this.rentalDays = rentalDays;

    }

    private void calculateRentalCost(float currentPrice) {
        
        rentalCost = (rentalDays * currentPrice) * consultRentedMovies().size(); 
        
    }

    public void rentMovie(float currentPrice) {

        calculateRentalCost(currentPrice);
        // falta marcar la pelicula como rentada

    }

    public String getRentalId() {
        return rentalId;
    }

    public boolean rentalState() {
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
    public void markAsRented(ArrayList<Movie> movies) {

        for(Movie movie : movies) {

            if (movie.isMovieAvaible() == false) { // Preguntamos si la pelicula no esta rentada, para poder rentarla.

                movie = true; // Queda rentada.

            }
        }

        // Si no, necesitamos lanzar excepcion?

    }

    public void markAsAvaible() {

        if (movieRented == true) { // Preguntamos si la pelicula esta rentada, para poder devolverla.
            
            movieRented = false; // Queda disponible.

        }

        // Si no, necesitamos lanzar excepcion?
    }
}
