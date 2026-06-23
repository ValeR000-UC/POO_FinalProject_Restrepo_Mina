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
            return false; // Para imprimir en el main.
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
                flag = true; // Alzamos la bandera! No se puede agregar
                break; // Detenemos la iteracion.
            }
        }
        if (!flag) {
            customers.add(customer); // Se agrega el customer.
            return true; // Operacion exitosa.
        }        
        else {
            return false;
        }

    }

    public boolean registerRental(Rental rental) {

        boolean flag = false;

        for (Rental r : rentals) {

            if ((r.getRentalId().equals(rental.getRentalId()))) {
                flag = true; // Alzamos la bandera! No se puede agregar
                break; // Detenemos la iteracion.
            }
            
        }
        
        if (!flag) {
            
            rentals.add(rental); // Se agrega la renta.
            return true;
        }

        else {
            return false;
        }
    }

    public boolean updateCurrentRentalPrice(int newPrice) {

        boolean flag = false;

        if ((0 < newPrice) && (newPrice <= 100)) {
            
            currentRentalPrice = newPrice; // Actualizamos el precio
            flag = true; // Alzamos la bandera.
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

            if (movie.getMovieName().toLowerCase().contains(movieName.toLowerCase())) { // Preguntamos si la pelicula contiene el nombre ingresado 
                
                busqueda.add(movie); // La agregamos a la lista.
            }
       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Genre> findGenreByName(String genreName) {
        
        ArrayList<Genre> busqueda = new ArrayList<>();

        for(Genre genre : genres) {

            if (genre.getGenreName().toLowerCase().contains(genreName.toLowerCase())) { // Preguntamos si nombre del genero contiene el nombre ingresado 
                
                busqueda.add(genre); // La agregamos a la lista.
            }
       }

       return busqueda; // Retornamos la lista.
    }

    public ArrayList<Customer> findCustomersByName(String customerName) {
        
        ArrayList<Customer> busqueda = new ArrayList<>();

        for(Customer customer : customers) {

            if (customer.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) { // Preguntar si el nombre el customer contiene algo de lo ingresado
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
                    
                    movies.remove(movie); // Eliminamos la pelicula.
                    flag = true;
                    break;
                }               
            }
        }

        return flag; // Para imprimir en el main.
    }

    public boolean deleteRental(String rentalId) {

        boolean flag = false;

        for (Rental rental : rentals) {
            
            if (rentalId.equals(rental.getRentalId())) {

                if (!rental.getRentalState()) {
                    
                    rentals.remove(rental); // Eliminamos la renta.
                    flag = true;
                    break;
                }
            }
        }

        return flag; // Para imprimir en el main.
    }

    public boolean deleteCustomer(String customerId) {

        boolean flag = false;

        for (Customer customer : customers) {
            
            if (customerId.equals(customer.getCustomerId())) {

                if (!customer.getCustomerState()) { // Preguntamos si tiene una renta a su nombre

                    customers.remove(customer); // Eliminamos el costumer.
                    flag = true; // Alzamos la bandera.
                    break; // Se termina la busqueda.                    
                }
            }
        }

        return flag; // Para imprimir en el main. 
    }
//COSO QUE RECOMIENDA
// Recomienda peliculas disponibles basandose en el genero mas rentado del sistema
    public ArrayList<Movie> recommendMovies() {

        // 1. Contar cuantas veces aparece cada genero en todas las rentas
        ArrayList<String> genreIds = new ArrayList<>();
        ArrayList<Integer> genreCounts = new ArrayList<>();

        for (Rental rental : rentals) {
            for (Movie movie : rental.consultRentedMovies()) {

                String genreId = movie.getMovieGenre().getGenreId();
                int index = genreIds.indexOf(genreId);

                if (index == -1) {
                    // genero nuevo, lo agrega
                    genreIds.add(genreId);
                    genreCounts.add(1);
                } else {
                    // genero ya existe, suma 1
                    genreCounts.set(index, genreCounts.get(index) + 1);
                }
            }
        }

        // 2. Si no hay rentas, no hay recomendacion
        if (genreIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 3. Encontrar el genero mas rentado
        int maxIndex = 0;
        for (int i = 1; i < genreCounts.size(); i++) {
            if (genreCounts.get(i) > genreCounts.get(maxIndex)) {
                maxIndex = i;
            }
        }

        String mostRentedGenreId = genreIds.get(maxIndex);

        // 4. Retornar peliculas disponibles de ese genero
        ArrayList<Movie> recommendations = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieGenre().getGenreId().equals(mostRentedGenreId) && !movie.isMovieRented()) {
                recommendations.add(movie);
            }
        }

        return recommendations;
    }
}
