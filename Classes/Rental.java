import java.util.ArrayList;

public class Rental {
    
    // Atributes
    private ArrayList<Movie> rentedMovies = new ArrayList<>();
    private User user;
    private Customer customer;
    private String rentalId;
    private boolean rentalActive = true; // Se crea en activa por defecto.
    private int rentalDays;
    private float rentalCost;

    // Constructor
    Rental(ArrayList<Movie> rentedMovies, User user, Customer customer, String rentalId, int rentalDays) {

        this.rentedMovies = rentedMovies;
        this.user = user;
        this.customer = customer;
        this.rentalId = rentalId;
        this.rentalDays = rentalDays;

    }

    private void calculateRentalCost(float currentPrice) { // Metodo que calcula el costo de la renta.
        
        rentalCost = (rentalDays * currentPrice) * consultRentedMovies().size(); 
        
    }

    public void processRentMovie(float currentPrice, ArrayList<Movie> movies) {

        calculateRentalCost(currentPrice); // Calculamos el costo de la renta de las peliculas.
        rent(movies); // Rentamos la pelicula.

    }

    public void processReturnMovie(ArrayList<Movie> movies) {

        returnMovie(movies); // Devolvemos la pelicula.
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
    private void rent(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
            movie.markAsRented(); // Si la pelicula estaba disponible, se renta.
        }
    }

    private void returnMovie(ArrayList<Movie> movies) {

        for(Movie movie : movies) {
            movie.markAsAvaible(); // Si la pelicula estaba rentada, se devuelve.
        }
    }

}
