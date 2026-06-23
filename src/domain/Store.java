package domain;
import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private String name;
    private float currentRentalPrice;

    private ArrayList<Movie> movies;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;
    private ArrayList<Genre> genres;

    // Constructor
    public Store(
    String name, float currentRentalPrice, ArrayList<Movie> movies, 
    ArrayList<Customer> customers, 
    ArrayList<Rental> rentals, ArrayList<Genre> genres
    ) {

        this.name = name; 
        this.currentRentalPrice = currentRentalPrice;
        this.movies = movies;
        this.customers = customers;
        this.rentals = rentals;
        this.genres = genres;

    }

    // Setters

    public void registerMovie(Movie movie) {
        
    }

    public void registerCustomer(Customer customer) {

    }

    public void registerRental(Rental rental) {

    }

    public void updateCurrentRentalPrice(float newPrice) {

        if ((0 < newPrice) && (newPrice <= 100)) {
            
            currentRentalPrice = newPrice; // Actualizamos el precio
        }
    }
    

    // Getters
    public String consultStoreName() {
        return name;
    }

    public ArrayList<Genre> consultAllGenres() {
        return genres;
    }

    public Movie findMovieById(String movieId) {
       
       for(Movie movie : movies) {

            if (movieId.equals(movie.getMovieId())) {
                
                return movie;    
            }

       }

       return null;
    }

    public Customer findCustomerById(String customerId) {
        
        for(Customer customer : customers) {

            if (customerId.equals(customer.getCustomerId())) {
                
                return customer;    
            }

       }

       return null;
    }

    public Rental findRentalById(String rentalId) {
        
        for(Rental rental : rentals) {

            if (rentalId.equals(rental.getRentalId())) {
                
                return rental;    
            }

       }

       return null;
    }

    public Genre findGenreById(String genreId) {
        
        for(Genre genre : genres) {

            if (genreId.equals(genre.getGenreId())) {
                
                return genre;    
            }
       }
       return null;
    }

    public ArrayList<Movie> findMoviesByName(String movieName) {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.getMovieName().contains(movieName)) { // Preguntamos si la pelicula contiene el nombre ingresado 
                
                busqueda.add(movie); // La agregamos a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Genre> findGenreByName(String genreName) {
        
        ArrayList<Genre> busqueda = new ArrayList<>();

        for(Genre genre : genres) {

            if (genre.getGenreName().contains(genreName)) { // Preguntamos si nombre del genero contiene el nombre ingresado 
                
                busqueda.add(genre); // La agregamos a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }


    public ArrayList<Customer> findCustomersByName(String customerName) {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerName().contains(customerName)) { // Preguntar si el nombre el customer contiene algo de lo ingresado
                busqueda.add(customer); // Lo agregamos a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Movie> findAvaibleMovies() {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.isMovieRented() == false) { // Preguntamos si movie NO esta rentada.
                
                busqueda.add(movie); // La agregamos a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Movie> findUnavaibleMovies() {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.isMovieRented() == true) { // Preguntar si movie esta rentada
                busqueda.add(movie); // La agregamos a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Customer> findActiveCustomers() {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerState() == true) { // Preguntar si customer esta activo
                busqueda.add(customer); // Agregar a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Customer> findInactiveCustomers() {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerState() == false) { // Preguntar si customer NO esta activo
                busqueda.add(customer); // Agregar a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Rental> findActiveRentals() {
        
        ArrayList<Rental> busqueda = new ArrayList<>();

        for(Rental rental : rentals) {

            if (rental.getRentalState() == true) { // Preguntar si rental esta activa
                busqueda.add(rental); // Agregar a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Rental> findInactiveRentals() {
        
        ArrayList<Rental> busqueda = new ArrayList<>();

        for(Rental rental : rentals) {

            if (rental.getRentalState() == false) { // Preguntar si rental NO esta activa
                busqueda.add(rental); // Agregar a la lista.
                
            }

       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Movie> findMoviesByGenre(Genre genre) {
        
         ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.getMovieGenre().equals(genre)) {
                
                busqueda.add(movie);
            }

        }

        return busqueda;
    }

    public ArrayList<Movie> consultAllMovies() {
        return movies;
    }

    public ArrayList<Customer> consultAllCustomers() {
        return customers;
    }

    public ArrayList<Rental> consultAllRentals() {
        return rentals;
    }

    public float consultRentalPrice() {
        return currentRentalPrice;
    }

    // Delete
    public void deleteMovie(String movieId) {

    }

    public void deleteRental(String rentalId) {

    }

    public void deleteCustomer(String customerId) {

    }

}
