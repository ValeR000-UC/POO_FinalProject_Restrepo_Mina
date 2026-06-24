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
    private Ui ui = new UiConsole(); // Hacemos que trabaje por consola, pero queda abierta para cualquier otra interfaz.

    // Creamos el contructor para no estar pidiendo las clases en cada metodo.
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
            option = videoStore(); // Mostramos el menu principal de la tienda.
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
                    break;

                case 6:
                    exportCSV();
                    break;

                case 7:
                    store.recommendMovies();

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
                break;

            case 8:
                processRemoveMovie();
                break;
            
            case 0:
                break;
        
            default:
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void printM(ArrayList<Movie> movies) {

        if (movies == null || movies.isEmpty()) {
            
            ui.showText("No hemos encontrado nada en la base de datos.");
            return; // Termina el metodo.
        }

        for(int i = 0 ; i < movies.size() ; i++) {

            ui.showText(movies.get(i).getMovieName() + "\t");

            if ((i + 1) % 4 == 0) {
                
                ui.newLine(); // Salto de linea.
            }
        }

        ui.newLine();
    }

    private void printC(ArrayList<Customer> customers) {

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

    private void processRemoveMovie() {

        String movieId = ui.inputText("ID de la pelicula: ");
        boolean confirmation = store.deleteMovie(movieId);

        if(confirmation) {
            ui.showText("Se ha eliminado correctamente!");
        }

        else {
            ui.showText("Error 302: Proceso fallido! La pelicula se encuentra rentada.");
        }
    }

    private void processRegisterMovie() {

        String movieName = ui.inputText("Nombre: ");
        ui.newLine();
        String year = ui.inputText("Fecha de lanzamiento: ");
        ui.newLine();
        String director = ui.inputText("Director: ");
        ui.newLine();
        Genre genre;

        do {
            
            String genreId = ui.inputText("ID de Genero: "); 
            genre = store.findGenreById(genreId); // Buscamos el objeto tipo genre.

            if(genre == null) {
                ui.showText("El ID: " + genreId + ", no existe en la base de datos."); // Si el genero NO existe.
            }

        } while (genre == null);

        String movieId;
        boolean flag;
        
        do {

            flag = false; // Reinicio de bandera.
            movieId = ui.inputText("ID de la pelicula: "); // Parametro para crear objeto Movie
            Movie m = store.findMovieById(movieId); // Para verificar que no exista.

            if(movieId.length() <= 0 || movieId.length() > 5) {
                ui.showText("Error 202: Dato invalido! Min: 1 | Max: 5"); 
                flag = true;
            }
            
            else if(m != null) { // Verificamos si encontro una pelicula con el mismo id.

                ui.showText("Error 301: El ID: " + movieId + " ya se encuentra registrado.");
                flag = true;
            }

        } while (flag);

        Movie movie = new Movie(movieName, movieId, year, director, genre); // Creamos el objeto.
        boolean confirmation = store.registerMovie(movie); // Registramos la pelicula.
        
        if (confirmation) {
            ui.showText("Registro exitoso!");
        }

        else {
            ui.showText("Error 305: Resgistro fallido.");
        }
    }

    private void printAllMovies() {

        ArrayList<Movie> movies = store.consultAllMovies(); // Traemos todas las peliculas.
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
                break;

            case 7:
                processRemoveCustomer();
                break;

            case 0:
                ui.showText("Gracias por visitarnos! Te esperamos pronto...");
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

    private void processRegisterCustomer() {

        String customerName = ui.inputText("Nombre del cliente: ");
        String customerId;
        boolean flag;

        do {
            
            flag = false; // Reinicio.
            customerId = ui.inputText("Cedula: ");

            if (customerId.length() < 10 || customerId.length() > 10) {
                
                ui.showText("Error 300: La cedula debe tener 10 digitos.");
                flag = true;
            }

        } while (flag);
        
        Customer customer = new Customer(customerName, customerId); // Creamos el objeto.
        boolean confirmation = store.registerCustomer(customer);

        if (confirmation) {
            
            ui.showText("Registro exitoso!");
        }

        else {
            ui.showText("Error 305.");
        }

    }

    private void processRemoveCustomer() {

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
                
                ui.newLine(); // Salto de linea.
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
                break;
            
            case 2:
                returnMovie();
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
                break;

            case 0:
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

    // Alquilar pelicula.
    private void rentMovie() {

        ArrayList<Movie> movies = moviesList();
        Customer customer = verifyCustomer();
        int rentalDays = verifyRentalDays();
        User user = store.getUser(); // Guardamos el objeto usuario para crear el objeto rental.
        String rentalId = createRentalId();

        // Creamos el objeto renta.
        Rental rental = new Rental(movies, user, customer, rentalId, rentalDays);
        rental.processRentMovie(store.consultRentalPrice(), movies); // Rentamos las peliculas.
        store.registerRental(rental); // Agregamos la renta a la lista.
    }

    // Devolver pelicula.
    private void returnMovie() {

        String rentalId = null;
        Rental rental = null;

        do {
            
            rentalId = ui.inputText("Ingrese el ID de la renta: ");
            rental = store.findRentalById(rentalId);

            if (rental == null) {
                
                ui.showText("Error 200: No se encontro ninguna renta con el ID: " + rentalId);
            }

            else if(rental.getRentalState() == false){ // Para saber si esta renta ya se desactivo.

                ui.showText("La renta: " + rental.getRentalId() + ", ya se encuentra desactivada.");
            }

        } while (rental == null || rental.getRentalState() == false);
        
        rental.processReturnMovie(rental.consultRentedMovies());

    }

    // Metodo para el parametro rentedMovies de la clase rental. 
    private ArrayList<Movie> moviesList() {

        ArrayList<Movie> movies = new ArrayList<>(); // Esta lista es la que se le pasa a Rental para crear el objeto.
        int numMovies = ui.inputInt("Ingrese la cantidad de peliculas: ");
        ui.newLine();

        for(int i = 1 ; i <= numMovies ; i++){

            Movie movie = null; // Variable temporal para guardar cada pelicula.

            // Bloque para garantizar que se ingrese un codigo valido.
            do {
                
                String movieId = ui.inputText("Ingrese el codigo de la pelicula numero " + i);
                movie = store.findMovieById(movieId); // Buscamos el objeto.
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

            movies.add(movie); // Vamos llenando la lista de acuerdo a la cantidad de peliculas.
            ui.showText(movie.getMovieName() + " Agregada correctamente."); // Informamos que todo salio bien.
        }

        return movies;
    }

    // Metodo para el parametro customer de la clase Rental.
    private Customer verifyCustomer() {

        Customer customer = null;
        // Bloque para verificar que el id sea correcto.
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

    // Metodo para el parametro rentalDays de la clase Rental.
    private int verifyRentalDays() {

        int rentalDays;
            // Bloque para verificar que rentalDays sea valido.
        do {
            
            rentalDays = ui.inputInt("Cantidad de dias: "); // Se la pasamos al objeto rental

            if ( (rentalDays <= 0) || (rentalDays > 20) ) {
                
                ui.showText("Error 202: Dato invalido.");
                ui.newLine();
                ui.showText("Minimo: 1 dia | Maximo: 20 dias.");
                ui.newLine();
                
            }

        } while ((rentalDays <= 0) || (rentalDays > 20));

    
        return rentalDays;
    }

    // Metodo para el parametro rentalId de la clase Rental.
    private String createRentalId() {

        Random random = new Random();
        String rentalId;
        Rental search; // Para buscar que no haya una renta con el mismo ID.
        
        // Bloque para generar el id de la renta.
        do {
            
            rentalId = String.valueOf(random.nextInt(100001)); // Genera un numero entre 0 y 100000 para los ids y lo guarda como String.
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

        Store tempStore = new Store(
            "Store",
            0,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            store.consultAllGenres(), // reuse existing genres
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
