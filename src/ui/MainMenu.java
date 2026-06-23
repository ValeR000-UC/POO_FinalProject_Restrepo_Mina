package ui;
import java.util.ArrayList;
import java.util.Random;
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
                    } while (option2 != 0);
                    break;

                case 3:
                    do {
                        option3 = rentals();
                        ui.newLine();
                    } while (option3 != 0);                    
                    break;

                case 4:
                    do {
                        option4 = settings();
                        ui.newLine();
                    } while (option4 != 0);
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
                searchMovieByName();
                break;

            case 6:
                searchMovieByGenre();
                break;

            case 7:
                processRegisterMovie();

            case 8:
                processRemoveMovie();
            
            case 0:
                break;
        
            default:
                ui.showText("Opcion invalida!");
                return;
        }
    }

    private void printM(ArrayList<Movie> movies) {

        if (movies == null) {
            
            ui.showText("No hemos encontrado nada en la base de datos.");
        }

        for(int i = 0 ; i < movies.size() ; i++) {

            ui.showText(movies.get(i).getMovieName() + "\t");

            if ((i + 1) % 4 == 0) {
                
                ui.newLine(); // Salto de linea.
            }
        }
    }

    private void printC(ArrayList<Customer> customers) {

        if (customers == null) {
            
            ui.showText("No hemos encontrado nada en la base de datos.");
        }

        for(int i = 0 ; i < customers.size() ; i++) {

            ui.showText(customers.get(i).getCustomerName() + "\t");

            if ((i + 1) % 4 == 0) {
                
                ui.newLine(); // Salto de linea.
            }
        }
    }

    private void processRemoveMovie() {

        String movieId = ui.inputText("ID de la pelicula: ");
        boolean confirmation = store.deleteMovie(movieId);

        if(confirmation) {
            ui.showText("Se ha eliminado correctamente!");
        }

        else {
            ui.showText("Proceso fallido! La pelicula se encuentra rentada.");
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
                ui.showText("Dato invalido! Min: 1 | Max: 5"); 
                flag = true;
            }
            
            else if(m != null) { // Verificamos si encontro una pelicula con el mismo id.

                ui.showText("El ID: " + movieId + " ya se encuentra registrado.");
                flag = true;
            }

        } while (flag);

        Movie movie = new Movie(movieName, movieId, year, director, genre); // Creamos el objeto.
        boolean confirmation = store.registerMovie(movie); // Registramos la pelicula.
        
        if (confirmation) {
            ui.showText("Registro exitoso!");
        }

        else {
            ui.showText("Resgistro fallido.");
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

        String movieId = ui.inputText("Ingrese el ID de la pelicula: ");
        Movie movie = store.findMovieById(movieId);

        if (movie == null) {
            
            ui.showText("La pelicula no se encuentra registrada en la base de datos.");
        }

        else {
            ui.showText("Resultado de busqueda: " + movie.getMovieName());
        }

    }

    private void searchMovieByName() {

        ArrayList<Movie> movies = store.findMoviesByName(null);
        printM(movies);

    }

    private void printAvaibleMovies() {

        ArrayList<Movie> movies = store.findAvaibleMovies();
        printM(movies);
    }

    private void searchMovieByGenre() {

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
            "USUARIO\n" + 
            "\n" + 
            "3. Ver información del usuario\n" + 
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

    // Alquilar pelicula.
    public void option1() {

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
    public void option2() {

        returnMovie();
    }

    private void returnMovie() {

        String rentalId = null;
        Rental rental = null;

        do {
            
            rentalId = ui.inputText("Ingrese el ID de la renta: ");
            rental = store.findRentalById(rentalId);

            if (rental == null) {
                
                ui.showText("No se encontro ninguna renta con el ID: " + rentalId);
            }

            else if(rental.rentalState() == false){ // Para saber si esta renta ya se desactivo.

                ui.showText("La renta: " + rental.getRentalId() + ", ya se encuentra desactivada.");
            }

        } while (rental == null || rental.rentalState() == false);
        
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
                
                    ui.showText("La pelicula: " + movie.getMovieName() + ", se encuentra rentada.");
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
                    
                    ui.showText("El cliente " +idCustomer+ ", no se encuentra en la base de datos.");
                    ui.newLine();
                }

                else if(customer.getCustomerState() == true) {

                    ui.showText("El cliente " +idCustomer+ ", tiene una renta a su nombre.");

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
                
                ui.showText("Dato invalido.");
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

    public void rentalPrice(){

    }
}
