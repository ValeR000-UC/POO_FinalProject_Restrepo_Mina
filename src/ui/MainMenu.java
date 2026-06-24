package ui;
import java.util.ArrayList;
import java.util.Random;

import data.CSVmanager;
import data.DataManager;
import domain.Customer;
import domain.Genre;
import domain.Movie;
import domain.Rental;
import domain.Store;
import domain.User;

public class MainMenu {
    
    private Store store;
    private Ui ui = new UiConsole(); // Use console for interaction, but keep it open for any other interface

    // Create constructor to avoid passing the classes in every method
    public MainMenu(Store store, Ui ui){

        this.store = store;
        this.ui = ui;

    }

    public void start() {

        int option;
        int option1;
        int option2;
        int option3;
        int option4;

        do {           
            option = videoStore(); //Show main menu
            ui.newLine();
            switch (option) {
                case 1:
                    do {                    
                        option1 = movies();
                        ui.newLine();
                        moviesMenu(option1);
                    } while (option1 != 0);
                    
                    break;
                
                case 2:
                    do {
                        option2 = customers();
                        ui.newLine();
                        customersMenu(option2);
                    } while (option2 != 0);
                    break;

                case 3:
                    do {
                        option3 = rentals();
                        ui.newLine();
                        rentalsMenu(option3);
                    } while (option3 != 0);                    
                    break;

                case 4:
                    do {
                        option4 = settings();
                        settingsMenu(option4);
                        ui.newLine();
                    } while (option4 != 0);
                    break;

                case 5:
                    importCSV();
                    DataManager.saveObject("store.dat", store);
                    break;

                case 6:
                    exportCSV();
                    DataManager.saveObject("store.dat", store);
                    break;

                case 7:
                    ArrayList<Movie> recommendations =
                        store.recommendMovies();

                    if (recommendations.isEmpty()) {

                        ui.showText("No hay recomendaciones disponibles");

                    } else {

                        ui.showText("===== TOP 3 RECOMENDACIONES =====");
                        ui.newLine();

                        int limit = Math.min(3, recommendations.size());

                        for (int i = 0; i < limit; i++) {

                            Movie movie = recommendations.get(i);

                            ui.showText(
                                (i + 1) + ". " +
                                movie.getMovieName() +
                                " (" + movie.getMovieYear() + ")" +
                                " | " +
                                movie.getMovieGenre().getGenreName()
                            );
                        }
                        ui.newLine();
                        ui.showText("==============================");
                    }

    break;

                case 0:
                    ui.showText("Gracias por visitarnos! Te esperamos pronto...");
                    break;
            
                default:
                    break;
            }

        } while (option != 0);
        
    }

    private int videoStore() {

        ui.showText(

            "=============== VIDEO STORE ===============\n" + 
            "\n" + 
            "1. Películas\n" + 
            "2. Clientes\n" + 
            "3. Rentas\n" + 
            "4. Configuración\n" +
            "5. Importar CSV\n" + 
            "6. Exportar CSV\n" +
            "7. Recomendaciones\n" + 
            "0. Salir\n" + 
            "\n" + 
            "===========================================\n" 
        );

        int option = ui.inputInt("Seleccione una opción: ");
        return option;
    }


    private int movies() {

        ui.showText(

            "========== PELICULAS ==========\n" + 
            "\n" + 
            "CONSULTAS\n" + 
            "\n" + 
            "1. Ver todas\n" + 
            "2. Ver disponibles\n" + 
            "3. Ver rentadas\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "BUSQUEDAS\n" + 
            "\n" + 
            "4. Buscar por ID\n" + 
            "5. Buscar por nombre\n" + 
            "6. Buscar por género\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "GESTION\n" + 
            "\n" + 
            "7. Registrar película\n" + 
            "8. Eliminar película\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "0. Volver\n" + 
            "\n" + 
            "=============================="
        );

        int option = ui.inputInt("Seleccione una opción: ");
        return option;
    }

    private void moviesMenu(int option1) {

        switch (option1) {
            case 1:
                printAllMovies();
                break;
            
            case 2:
                printAvaibleMovies();
                break;

            case 3:
                printUnavaibleMovies();
                break;

            case 4:
                searchMovieById();               
                break;

            case 5:
                searchMoviesByName();
                break;

            case 6:
                searchMoviesByGenre();
                break;

            case 7:
                processRegisterMovie();
                DataManager.saveObject("store.dat", store);
                break;

            case 8:
                processRemoveMovie();
                DataManager.saveObject("store.dat", store);
                break;
            
            case 0:
                break;
        
            default:
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void printM(ArrayList<Movie> movies) { // Display movies in rows of 4 and show a message if the list is empty

        if (movies == null || movies.isEmpty()) {
            
            ui.showText("No hemos encontrado nada en la base de datos.");
            return; // Termina el metodo.
        }

        for(int i = 0 ; i < movies.size() ; i++) {

            ui.showText(movies.get(i).getMovieName() + "\t");

            if ((i + 1) % 4 == 0) {
                
                ui.newLine();
            }
        }

        ui.newLine();
    }

    private void printC(ArrayList<Customer> customers) { // Display customers in rows of 4 and show a message if the list is empty

        if (customers == null || customers.isEmpty()) {

            ui.showText("Error 200: No hemos encontrado nada en la base de datos.");
            return;
        }

        for(int i = 0; i < customers.size(); i++) {

            ui.showText(customers.get(i).getCustomerName() + "\t");

            if ((i + 1) % 4 == 0) {

                ui.newLine();
            }
        }

        ui.newLine();
    }

    private void processRemoveMovie() { // Remove a movie and show whether the operation was successful

        String movieId = ui.inputText("ID de la pelicula: ");
        boolean confirmation = store.deleteMovie(movieId);

        if(confirmation) {
            ui.showText("Se ha eliminado correctamente!");
        }

        else {
            ui.showText("Error 302: Proceso fallido! La pelicula se encuentra rentada.");
        }
    }

    private void processRegisterMovie() { // Register a movie by collecting data, validating inputs, and saving it to the store

        String movieName = ui.inputText("Nombre: ");
        ui.newLine();
        String year = ui.inputText("Fecha de lanzamiento: ");
        ui.newLine();
        String director = ui.inputText("Director: ");
        ui.newLine();
        Genre genre;

        do {
            
            String genreId = ui.inputText("ID de Genero: "); 
            genre = store.findGenreById(genreId); 

            if(genre == null) {
                ui.showText("El ID: " + genreId + ", no existe en la base de datos."); // if the genre doesn't exists
            }

        } while (genre == null);

        String movieId;
        boolean flag;
        
        do {

            flag = false; // Reinicio de bandera.
            movieId = ui.inputText("ID de la pelicula: "); 
            Movie m = store.findMovieById(movieId); 

            if(movieId.length() <= 0 || movieId.length() > 5) {
                ui.showText("Error 202: Dato invalido! Min: 1 | Max: 5"); 
                flag = true;
            }
            
            else if(m != null) { // Check if a movie with the same ID already exists

                ui.showText("Error 301: El ID: " + movieId + " ya se encuentra registrado.");
                flag = true;
            }

        } while (flag);

        Movie movie = new Movie(movieName, movieId, year, director, genre); //Creates the objects
        boolean confirmation = store.registerMovie(movie); 
        
        if (confirmation) {
            ui.showText("Registro exitoso!");
        }

        else {
            ui.showText("Error 305: Resgistro fallido.");
        }
    }

    private void printAllMovies() {

        ArrayList<Movie> movies = store.consultAllMovies();
        printM(movies);
    }

    private void printUnavaibleMovies() {

        ArrayList<Movie> movies = store.findUnavaibleMovies();
        printM(movies);
    }

    private void searchMovieById() {

        String movieId = ui.inputText("ID de la pelicula: ");
        Movie movie = store.findMovieById(movieId);

        if (movie == null) {
            
            ui.showText("Error 200: La pelicula no se encuentra registrada en la base de datos.");
        }

        else {
            ui.showText("Resultado de busqueda: " + movie.getMovieName());
        }

    }

    private void searchMoviesByName() {

        String movieName = ui.inputText("Nombre de la pelicula: ");
        ArrayList<Movie> movies = store.findMoviesByName(movieName);
        printM(movies);

    }

    private void printAvaibleMovies() {

        ArrayList<Movie> movies = store.findAvaibleMovies();
        printM(movies);
    }

    private void searchMoviesByGenre() {

        String idGenre = ui.inputText("Ingrese el ID del genero: ");
        ArrayList<Movie> movies = store.findMoviesByGenre(idGenre);
        printM(movies);
    }

    private int customers() {

        ui.showText(

            "========== CLIENTES ==========\n" + 
            "\n" + 
            "CONSULTAS\n" + 
            "\n" + 
            "1. Ver todos\n" + 
            "2. Ver activos\n" + 
            "3. Ver inactivos\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "BUSQUEDAS\n" + 
            "\n" + 
            "4. Buscar por ID\n" + 
            "5. Buscar por nombre\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "GESTION\n" + 
            "\n" + 
            "6. Registrar cliente\n" + 
            "7. Eliminar cliente\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "0. Volver\n" + 
            "\n" + 
            "=============================="
        );

        int option = ui.inputInt("Seleccione una opción: ");
        return option;
    }

    private void customersMenu(int option2) {

        switch (option2) {
            case 1:
                printAllCustomers();
                break;
            
            case 2:
                printActiveCustomers();
                break;

            case 3:
                printInactiveCustomers();
                break;

            case 4:
                searchCustomerById();               
                break;

            case 5:
                searchCustomersByName();
                break;

            case 6:
                processRegisterCustomer();
                DataManager.saveObject("store.dat", store);
                break;

            case 7:
                processRemoveCustomer();
                DataManager.saveObject("store.dat", store);
                break;

            case 0:
                ui.showText("Volviendo al menu principal...");
                DataManager.saveObject("store.dat", store);
                break;
        
            default:  
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void searchCustomerById() {
        
        String customerId = ui.inputText("ID del cilente: ");
        Customer customer = store.findCustomerById(customerId);

        if (customer == null) {
            
            ui.showText("Error 200: El cliente no se encuentra registrado en la base de datos.");
        }

        else {
            ui.showText("Resultado de busqueda: " + customer.getCustomerName());
        }
    }

    private void searchCustomersByName() {

        String name = ui.inputText("Nombre del cliente: ");
        ArrayList<Customer> customer = store.findCustomersByName(name);
        printC(customer);
    }

    private void printAllCustomers() {

        ArrayList<Customer> customers = store.consultAllCustomers();
        printC(customers);
    }

    private void printActiveCustomers() {

        ArrayList<Customer> customers = store.findActiveCustomers();
        printC(customers);
        
    }

    private void printInactiveCustomers() {

        ArrayList<Customer> customers = store.findInactiveCustomers();
        printC(customers);
    }

    private void processRegisterCustomer() { // Register a customer by validating data and saving it to the store

        String customerName = ui.inputText("Nombre del cliente: ");
        String customerId;
        boolean flag;

        do {
            
            flag = false; 
            customerId = ui.inputText("Cedula: ");

            if (customerId.length() < 10 || customerId.length() > 10) {
                
                ui.showText("Error 300: La cedula debe tener 10 digitos.");
                flag = true;
            }

        } while (flag);
        
        Customer customer = new Customer(customerName, customerId); 
        boolean confirmation = store.registerCustomer(customer);

        if (confirmation) {
            
            ui.showText("Registro exitoso!");
        }

        else {
            ui.showText("Error 305.");
        }

    }

    private void processRemoveCustomer() { // Remove a customer and show whether the operation was successful
 
        String customerId = ui.inputText("Cedula del cliente: ");
        boolean confirmation = store.deleteCustomer(customerId);

        if (confirmation) {
            ui.inputText("Operacion exitosa!");
        }

        else {           
            ui.inputText("Error 304: Operacion fallida!");
        }
    }

    private void printR(ArrayList<Rental> rentals) {

        if (rentals == null || rentals.isEmpty()) {
            
            ui.showText("Error 200: No hemos encontrado nada en la base de datos.");
            return;
        }

        for(int i = 0 ; i < rentals.size() ; i++) {

            ui.showText(rentals.get(i).getRentalId() + "\t");

            if ((i + 1) % 4 == 0) {
                
                ui.newLine();
            }
        }

        ui.newLine();
    }

    private int rentals() {

        ui.showText(

            "=========== RENTAS ===========\n" +
            "\n" + 
            "OPERACIONES\n" + 
            "\n" + 
            "1. Alquilar película\n" + 
            "2. Devolver película\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "CONSULTAS\n" + 
            "\n" +
            "3. Ver todas las rentas\n" + 
            "4. Ver rentas activas\n" + 
            "5. Ver rentas inactivas\n" + 
            "\n" +
            "------------------------------\n" + 
            "\n" + 
            "BUSQUEDAS\n" + 
            "\n" + 
            "6. Buscar renta por ID\n" + 
            "\n" + 
            "------------------------------\n" + 
            "\n" + 
            "GESTION\n" + 
            "\n" +
            "7. Eliminar renta\n" + 
            "\n" +
            "------------------------------\n" + 
            "\n" + 
            "0. Volver\n" + 
            "\n" + 
            "=============================="
        );

        int option = ui.inputInt("Seleccione una opción: ");
        return option;

    }

    private void rentalsMenu(int option3) {

        switch (option3) {
            case 1:
                rentMovie();
                DataManager.saveObject("store.dat", store);
                break;
            
            case 2:
                returnMovie();
                DataManager.saveObject("store.dat", store);
                break;
                
            case 3:
                printAllRentals();
                break;

            case 4:
                printActiveRentals();              
                break;

            case 5:
                printInactiveRentals();
                break;

            case 6:
                searchRentalById();
                break;

            case 7:
                removeRental();
                DataManager.saveObject("store.dat", store);
                break;

            case 0:
                DataManager.saveObject("store.dat", store);
                break;
        
            default:
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void searchRentalById() {

        String rentalId = ui.inputText("ID de la pelicula: ");
        Rental rental = store.findRentalById(rentalId);

        if (rental == null) {
            
            ui.showText("Error 200: La renta no se encuentra registrada en la base de datos.");
        }

        else {
                ArrayList<Movie> movies = rental.consultRentedMovies();
                ui.showText("PELICULAS\n");

                for(Movie movie : movies) {
                    ui.showText(movie.getMovieName() + "\n");
                }

                ui.showText("\n");
                ui.showText("PROPIETARIO: " + rental.getCustomer().getCustomerName() + "\n");
                ui.showText("VALOR: " + rental.getRentalCost() + "\n");
                ui.showText("DIAS: " + rental.getRentalDays() + "\n");
                ui.showText("ESTADO: " + rental.getRentalState() + "\n");
        }
    }

    private void printAllRentals() {

        ArrayList<Rental> rentals = store.consultAllRentals();
        printR(rentals);
    }

    private void printActiveRentals() {

        ArrayList<Rental> rentals = store.findActiveRentals();
        printR(rentals);
    }

    private void printInactiveRentals() {

        ArrayList<Rental> rentals = store.findInactiveRentals();
        printR(rentals);
    }

    private int settings() {

        ui.showText(

            "======== CONFIGURACION ========\n" + 
            "\n" + 
            "PRECIO DE RENTA\n" + 
            "\n" + 
            "1. Consultar precio actual\n" + 
            "2. Actualizar precio\n" + 
            "\n" + 
            "-------------------------------\n" + 
            "\n" + 
            "0. Volver\n" + 
            "\n" + 
            "==============================="
        );

        int option = ui.inputInt("Seleccione una opción: ");
        return option;
    }

    private void settingsMenu(int option4) {

        switch (option4) {
            case 1:
                printRentalPrice();
                break;
            
            case 2:
                uptadeRentalPrice();
                DataManager.saveObject("store.dat", store);
                break;
                
            case 0:
                break;

            default:
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void printRentalPrice() {

        int price = store.consultRentalPrice();
        ui.showText("PRECIO ACTUAL: " + price);

    }

    private void uptadeRentalPrice() {
        boolean confirmation;

        do {

            int price = ui.inputInt("NUEVO PRECIO: ");
            confirmation = store.updateCurrentRentalPrice(price);
        } while (!confirmation);

    }


    private void rentMovie() {

        ArrayList<Movie> movies = moviesList();
        Customer customer = verifyCustomer();
        int rentalDays = verifyRentalDays();
        User user = store.getUser(); // Get user object to create the rental object
        String rentalId = createRentalId();

        //Creates rental obj
        Rental rental = new Rental(movies, user, customer, rentalId, rentalDays);
        rental.processRentMovie(store.consultRentalPrice(), movies); //rent the movie
        store.registerRental(rental);
    }

    private void returnMovie() {

        String rentalId = null;
        Rental rental = null;

        do {
            
            rentalId = ui.inputText("Ingrese el ID de la renta: ");
            rental = store.findRentalById(rentalId);

            if (rental == null) {
                
                ui.showText("Error 200: No se encontro ninguna renta con el ID: " + rentalId);
            }

            else if(rental.getRentalState() == false){ 

                ui.showText("La renta: " + rental.getRentalId() + ", ya se encuentra desactivada.");
            }

        } while (rental == null || rental.getRentalState() == false);
        
        rental.processReturnMovie(rental.consultRentedMovies());

    }


    private ArrayList<Movie> moviesList() { // Create and return a list of valid and available movies for rental

        ArrayList<Movie> movies = new ArrayList<>(); // This list is passed to Rental to create the object
        int numMovies = ui.inputInt("Ingrese la cantidad de peliculas: ");
        ui.newLine();

        for(int i = 1 ; i <= numMovies ; i++){

            Movie movie = null; // Temporary variable to store each movie

            // Block to ensure a valid code is entered
            do {
                
                String movieId = ui.inputText("Ingrese el codigo de la pelicula numero " + i);
                movie = store.findMovieById(movieId);
                ui.newLine();

                if (movie == null) {
                    
                    ui.showText("La pelicula: " + movieId + ", no se encuentra en el catalogo.");
                    ui.newLine();
                }

                else if (movie.isMovieRented() == true) {
                
                    ui.showText("Error 301! La pelicula: " + movie.getMovieName() + ", se encuentra rentada.");
                    ui.newLine();
                }

            } while (movie == null || movie.isMovieRented() == true);

            movies.add(movie);
            ui.showText(movie.getMovieName() + " Agregada correctamente.");
        }

        return movies;
    }


    private Customer verifyCustomer() {// Validate customer and return it only if it exists and has no active rental

        Customer customer = null;

        do {
                String idCustomer = ui.inputText("Id del Cliente: ");
                customer = store.findCustomerById(idCustomer);
                ui.newLine();

                if(customer == null) {
                    
                    ui.showText("Error 200! El cliente: " + idCustomer + ", no se encuentra en la base de datos.");
                    ui.newLine();
                }

                else if(customer.getCustomerState() == true) {

                    ui.showText("Error 300! El cliente " + idCustomer + ", tiene una renta a su nombre.");

                }

            } while (customer == null || customer.getCustomerState() == true);
            
        return customer;
    } 


    private int verifyRentalDays() { // Validate rental days and return a value between 1 and 20

        int rentalDays;

        do {
            
            rentalDays = ui.inputInt("Cantidad de dias: ");

            if ( (rentalDays <= 0) || (rentalDays > 20) ) {
                
                ui.showText("Error 202: Dato invalido.");
                ui.newLine();
                ui.showText("Minimo: 1 dia | Maximo: 20 dias.");
                ui.newLine();
                
            }

        } while ((rentalDays <= 0) || (rentalDays > 20));

    
        return rentalDays;
    }

    private String createRentalId() {

        Random random = new Random();
        String rentalId;
        Rental search; 
        

        do {
            
            rentalId = String.valueOf(random.nextInt(100001)); // Generate a number between 0 and 100000 for IDs and store it as String
            search = store.findRentalById(rentalId); 
            ui.newLine();

        } while (search != null);

        return rentalId;
    }

    private void removeRental(){

        String rentalId = ui.inputText("Ingrese el ID: ");
        boolean confirmation = store.deleteRental(rentalId);

        if (confirmation) {
            ui.showText("Proceso exitoso!");
        }

        else{
            ui.showText("Error 304! Proceso fallido.");
        }
    }

    public void importCSV() {

    String[][] movieMatrix =
        CSVmanager.loadCSVToMMovies("movies.csv");

    String[][] customerMatrix =
        CSVmanager.loadCSVToMCustomer("customers.csv");

    String[][] rentalMatrix =
        CSVmanager.loadCSVToM("rentals.csv");

    if (
        movieMatrix == null ||
        customerMatrix == null ||
        rentalMatrix == null
    ) {
        ui.showText("CSV files not found. Export data first.");
        return;
    }

    Store tempStore = new Store(
        "Store",
        0,
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        store.consultAllGenres(),
        null
    );

    ArrayList<Movie> movies =
        CSVmanager.matrixToMovies(
            movieMatrix,
            tempStore
        );

    ArrayList<Customer> customers =
        CSVmanager.matrixToCustomer(
            customerMatrix
        );

    Store storeWithData = new Store(
        "Store",
        0,
        movies,
        customers,
        new ArrayList<>(),
        store.consultAllGenres(),
        null
    );

    ArrayList<Rental> rentals =
        CSVmanager.matrixToRentals(
            rentalMatrix,
            storeWithData
        );

    store = new Store(
        "Store",
        0,
        movies,
        customers,
        rentals,
        store.consultAllGenres(),
        null
    );

    ui.showText("Importación exitosa!");
}
    public void exportCSV() {

        CSVmanager.saveMoviesMToCSV(
            "movies.csv",
            CSVmanager.exportMovieToM(store.consultAllMovies())
        );

        CSVmanager.saveCustomerMToCSV(
            "customers.csv",
            CSVmanager.exportCustomerToM(store.consultAllCustomers())
        );

        CSVmanager.saveRentalMToCSV(
            "rentals.csv",
            CSVmanager.exportRentalToM(store.consultAllRentals())
        );

        ui.showText("Exportación exitosa!");
    }
}
