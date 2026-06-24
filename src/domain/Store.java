package domain;
import java.io.Serializable;
import java.util.ArrayList;


//Central class of the system. Acts as the main data container, holding all collections of movies, customers, rentals and genres. 
//Provides all registration, search, listing and deletion operations used throughout the application.

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributes
    private String name;
    private int currentRentalPrice;
    private ArrayList<Movie> movies = null;
    private ArrayList<Customer> customers = null;
    private ArrayList<Rental> rentals = null;
    private ArrayList<Genre> genres = null;
    private User user;

    // Constructor
    public Store(
    String name, int currentRentalPrice, ArrayList<Movie> movies, 
    ArrayList<Customer> customers, 
    ArrayList<Rental> rentals, ArrayList<Genre> genres, User user
    ) {
        this.name = name; 
        this.user = user;
        this.currentRentalPrice = currentRentalPrice;
        this.movies = movies;
        this.customers = customers;
        this.rentals = rentals;
        this.genres = genres;
    }

    // Setters

    public boolean registerMovie(Movie movie) {
        boolean flag = false;

        for (Movie m : movies) {

            if ((m.getMovieId().equals(movie.getMovieId()))) {
                flag = true; // duplicate found, cannot register
                break; // stops iteration, no need to keep checking
            }
        }
        if (!flag) {
            movies.add(movie);
            return true; // UI uses this to print success or failure message
        } 
        else {
            return false; // Used in main to show result    
    }
    }
    public boolean registerGenre(Genre genre) {
        boolean flag = false;

        for (Genre g : genres) {

            //case insensitive comparison to avoid duplicate genres with different casing
            if ((g.getGenreId().toLowerCase().equals(genre.getGenreId().toLowerCase()))) {
            flag = true;
                flag = true;
                break;
            }
        }
        if (!flag) {
            
            genres.add(genre); 
            return true;
        }
        else {
            return false; }
    }

    public boolean registerCustomer(Customer customer) {
    boolean flag = false;

        for (Customer c : customers) {
            if ((c.getCustomerId().equals(customer.getCustomerId()))) {
                flag = true; // Set flag to true, movie cannot be added
                break; // Stop iteration
            }
        }
        if (!flag) {
            customers.add(customer); // Add customer
            return true; // Operation successful
        }        
        else {
            return false;
        }

    }

    public boolean registerRental(Rental rental) {

        boolean flag = false;

        for (Rental r : rentals) {

            if ((r.getRentalId().equals(rental.getRentalId()))) {
                flag = true; // Set flag to true, movie cannot be added
                break; 
            } 
        }
        
        if (!flag) {
            
            rentals.add(rental); //Add rental
            return true;
        }
        else {
            return false;
        }
    }

    public boolean updateCurrentRentalPrice(int newPrice) {

        boolean flag = false;

        if ((0 < newPrice) && (newPrice <= 100)) {
            
            currentRentalPrice = newPrice; //Update price
            flag = true; //Set flag
        }
        return flag;
    }
    

    // Getters
    public String consultStoreName() {
        return name;
    }

    public User getUser() {
        return user;
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

            if (genreId.toLowerCase().equals(genre.getGenreId().toLowerCase())) {
                
                return genre;    
            }
       }
       return null;
    }

    public ArrayList<Movie> findMoviesByName(String movieName) {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.getMovieName().toLowerCase().contains(movieName.toLowerCase())) { //Ask if the movie has the name
                
                busqueda.add(movie); //add to the list
            }
       }

       return busqueda;} //return the list

    public ArrayList<Genre> findGenreByName(String genreName) {
        ArrayList<Genre> busqueda = new ArrayList<>();

        for(Genre genre : genres) {

            if (genre.getGenreName().toLowerCase().contains(genreName.toLowerCase())) { // Check if the genre name contains the entered name
                
                busqueda.add(genre);
            }
       }

       return busqueda;}

    public ArrayList<Customer> findCustomersByName(String customerName) {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) { // Check if the customer name contains the entered name
                
            }

       }

       return busqueda;
    }

    public ArrayList<Movie> findAvaibleMovies() {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.isMovieRented() == false) { // Ask if movie is not rented
                
                busqueda.add(movie); // add to the list
                
            }

       }

       return busqueda; 
    }

    public ArrayList<Movie> findUnavaibleMovies() {
        
        ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.isMovieRented() == true) { // Ask if movie is rented
                busqueda.add(movie);
                
            }

       }

       return busqueda;
    }

    public ArrayList<Customer> findActiveCustomers() {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerState() == true) { //Ask if customer is active
                busqueda.add(customer);
                
            }
       }
       return busqueda; }

    public ArrayList<Customer> findInactiveCustomers() {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerState() == false) { // Ask if customer is NOT active
                busqueda.add(customer); 
                
            }
       }

       return busqueda;
    }

    public ArrayList<Rental> findActiveRentals() {
        
        ArrayList<Rental> busqueda = new ArrayList<>();

        for(Rental rental : rentals) {

            if (rental.getRentalState() == true) { //Ask if the rent is active
                busqueda.add(rental);
                
            }
       }
       return busqueda;
    }

    public ArrayList<Rental> findInactiveRentals() {
        
        ArrayList<Rental> busqueda = new ArrayList<>();

        for(Rental rental : rentals) {

            if (rental.getRentalState() == false) {
                busqueda.add(rental);
                
            }

       }
       return busqueda;
    }

    public ArrayList<Movie> findMoviesByGenre(String genreId) {
        
         ArrayList<Movie> busqueda = new ArrayList<>();

        for(Movie movie : movies) {

            if (movie.getMovieGenre().getGenreId().toLowerCase().equals(genreId.toLowerCase())) {
                
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

    public ArrayList<Genre> consultAllGenres() {
        return genres;
    }

    public int consultRentalPrice() {
        return currentRentalPrice;
    }

    // Delete
    public boolean deleteMovie(String movieId) {

        boolean flag = false;

        for (Movie movie : movies) {
            
            if (movieId.equals(movie.getMovieId())) {
                
                if (!movie.isMovieRented()) {
                    
                    movies.remove(movie);
                    flag = true;
                    break;
                }               
            }
        }

        return flag;
    }

    public boolean deleteRental(String rentalId) {

        boolean flag = false;

        for (Rental rental : rentals) {
            
            if (rentalId.equals(rental.getRentalId())) {

                if (!rental.getRentalState()) {
                    
                    rentals.remove(rental);
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    public boolean deleteCustomer(String customerId) {

        boolean flag = false;

        for (Customer customer : customers) {
            
            if (customerId.equals(customer.getCustomerId())) {

                if (!customer.getCustomerState()) { // Check if the customer has a rental under their name

                    customers.remove(customer);
                    flag = true;
                    break;                 
                }
            }
        }

        return flag; }
// Recommends movies based on genres currently rented
    public ArrayList<Movie> recommendMovies() {

        ArrayList<String> rentedGenres = new ArrayList<>();

        // Collect genres from rented movies
        for (Movie movie : movies) {

            if (movie.isMovieRented()) {// only use currently rented movies
                String genre =movie.getMovieGenre().getGenreId(); // gets movie genre ID

                if (!rentedGenres.contains(genre)) { // avoids duplicate genres
                    rentedGenres.add(genre); // stores genre for recommendations
                }
            }
        }
        ArrayList<Movie> recommendations =new ArrayList<>();

        // Suggest available movies from those genres
        for (Movie movie : movies) { // checks all movies in the store
            if (
                rentedGenres.contains(movie.getMovieGenre().getGenreId())&&!movie.isMovieRented()) {//movie genre matches rented genres and movie must be available
                recommendations.add(movie); //adds movie to recommendations
            }
        }
        return recommendations;
    }
}
