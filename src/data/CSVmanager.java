package data;
import domain.*; 
import java.io.*;
import java.util.ArrayList;

//esta clase es la encargada de hacer para cada clase del sistema los metodos de texto a CSV y de CSV a texto Exportar (objetos → CSV)
//Importar (CSV → objetos)
public class CSVmanager {
//Movie
    //1. Lista a matriz
    public static String[][] exportMovieToM(ArrayList<Movie> movies) {
    String[][] matrix = new String[movies.size()][7]; //Se le pasa la lista de peliculas crea la matriz y hace una fila por cada movie  y los espacios para la cantidad de atributos q son 6

    for (int i= 0; i< movies.size(); i++) { //recorre la lista de peliculas

        Movie mov= movies.get(i); //saca la cus en esa posicion y lo guarda
        matrix[i][0] = mov.getMovieName(); //en la fila i columna 0 pone el nombre
        matrix[i][1] = mov.getMovieId();
        matrix[i][2] = String.valueOf(mov.isMovieRented()); //convierte un bool o int a un string pq es matriz de string
        matrix[i][3] = mov.getMovieYear();
        matrix[i][4]= mov.getMovieDirector();

        matrix[i][5]= mov.getMovieGenre().getGenreName();
        matrix[i][6]= mov.getMovieGenre().getGenreId();
    }
    return matrix;
}
    //2. matriz a CSV
    public static boolean saveMoviesMToCSV(String filename, String[][] matrix) {

    StringBuilder sb= new StringBuilder(); //sirve pa construir strings eficientemente

    for (int i = 0; i <matrix.length; i++) {
        for (int j = 0; j <matrix[i].length; j++) { //recorre la matriz

            sb.append(matrix[i][j]); //agrega el dato actual al texto

            if (j < matrix[i].length - 1) {
                sb.append(","); //lo va separando por comas
            }
        }
        sb.append(System.lineSeparator());// agrega salto de linea para que la sig fila empiece abajo
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) { //abre el archivo y le mete todo lo que se construyo con sb
        bw.write(sb.toString());
        return true;

    } catch (IOException e) {
        System.err.println("Error guardando CSV... " + e.getMessage()); //si explota sale msj 
        return false;
    }
}
    //3. IMPORTAR csv a matriz
    public static String[][] loadCSVToMMovies(String filename) {

    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }
    } catch (IOException e) {
        System.err.println("Error reading CSV: " + e.getMessage());
        return null;
    }
    String[] rows = sb.toString().split(System.lineSeparator());
    String[][] matrix = new String[rows.length][];

    for (int i = 0; i < rows.length; i++) {
        matrix[i] = rows[i].split(",");
    }
    return matrix;
}
//matriz a objeto
    public static ArrayList<Movie> matrixToMovies(String[][] matrix, Store store) {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String name = matrix[i][0];
            String id = matrix[i][1];
            boolean rented = Boolean.parseBoolean(matrix[i][2]);
            String year = matrix[i][3];
            String director = matrix[i][4];

            String genreName= matrix[i][5];
            String genreId = matrix[i][6];

            Genre genre = store.findGenreById(genreId);
            if (genre == null) {
                continue; // ignorar movie
            }
            Movie mov = new Movie(name, id, year, director, genre);
            if (rented) {
                mov.markAsRented();
            }
            movies.add(mov);
        }
        return movies;
}

//Customer
    //1. Lista a matriz a CSV
    public static String[][] exportCustomerToM(ArrayList<Customer> customers) {
    String[][] matrix = new String[customers.size()][3]; //Se le pasa la lista de peliculas crea la matriz y hace una fila por cada movie  y los espacios para la cantidad de atributos q son 6

    for (int i= 0; i< customers.size(); i++) {

        Customer cus= customers.get(i); 
        matrix[i][0] = cus.getCustomerName(); 
        matrix[i][1] = cus.getCustomerId();
        matrix[i][2] = String.valueOf(cus.getCustomerState());
    }
    return matrix; 
}
    //2. matriz a CSV
    public static boolean saveCustomerMToCSV(String filename, String[][] matrix) {

    StringBuilder sb= new StringBuilder(); //sirve pa construir strings eficientemente

    for (int i = 0; i <matrix.length; i++) {
        for (int j = 0; j <matrix[i].length; j++) { //recorre la matriz

            sb.append(matrix[i][j]); //agrega el dato actual al texto

            if (j < matrix[i].length - 1) {
                sb.append(","); //lo va separando por comas
            }
        }
        sb.append(System.lineSeparator());// agrega salto de linea para que la sig fila empiece abajo
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) { //abre el archivo y le mete todo lo que se construyo con sb
        bw.write(sb.toString());
        return true;

    } catch (IOException e) {
        System.err.println("Error guardando CSV... " + e.getMessage()); //si explota sale msj 
        return false;
    }
}
    //3, IMPORTAR CSV a matriz
    public static String[][] loadCSVToMCustomer(String filename) {

    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }
    } catch (IOException e) {
        System.err.println("Error reading CSV: " + e.getMessage());
        return null;
    }
    String[] rows = sb.toString().split(System.lineSeparator());
    String[][] matrix = new String[rows.length][];

    for (int i = 0; i < rows.length; i++) {
        matrix[i] = rows[i].split(",");
    }
    return matrix;
}
    //matriz a objeto y crea lista objetos
    public static ArrayList<Customer> matrixToCustomer(String[][] matrix) {
        ArrayList<Customer> customers = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String customerName = matrix[i][0];
            String customerId = matrix[i][1];
            boolean customerState = Boolean.parseBoolean(matrix[i][2]);

            Customer cus = new Customer(customerName, customerId);
            if (customerState) {
                cus.changeCustomerState();
            }
            customers.add(cus);
        }
        return customers;
    }

//Genre
    public static String[][] exportGenreToM(ArrayList<Genre> genres) {
        String[][] matrix = new String[genres.size()][2]; //Se le pasa la lista de peliculas crea la matriz y hace una fila por cada movie  y los espacios para la cantidad de atributos q son 6

        for (int i= 0; i< genres.size(); i++) {

            Genre gen = genres.get(i);
            matrix[i][0] = gen.getGenreName(); 
            matrix[i][1] = gen.getGenreId();
        }
        return matrix; 
    }
    public static boolean saveGenreMToCSV(String filename, String[][] matrix) {
        StringBuilder sb= new StringBuilder(); //sirve pa construir strings eficientemente

        for (int i = 0; i <matrix.length; i++) {
            for (int j = 0; j <matrix[i].length; j++) { //recorre la matriz

                sb.append(matrix[i][j]); //agrega el dato actual al texto

                if (j < matrix[i].length - 1) {
                    sb.append(","); //lo va separando por comas
                }
            }
            sb.append(System.lineSeparator());// agrega salto de linea para que la sig fila empiece abajo
        }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) { //abre el archivo y le mete todo lo que se construyo con sb
        bw.write(sb.toString());
        return true;

    } catch (IOException e) {
        System.err.println("Error guardando CSV... " + e.getMessage()); //si explota sale msj 
        return false;
    }
}
    public static String[][] loadCSVToMGenre(String filename) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return null;
        }
        String[] rows = sb.toString().split(System.lineSeparator());
        String[][] matrix = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }
        return matrix;
    }
    public static ArrayList<Genre> matrixToGenres(String[][] matrix, Store store) {
        ArrayList<Genre> genres = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {

            String genreName = matrix[i][0];
            String genreId = matrix[i][1];

            Genre gen = new Genre(genreName, genreId);
            genres.add(gen);

        }
        return genres;
}
    
//Rental
    public static String[][] exportRentalToM(ArrayList<Rental> rentals) {
        String[][] matrix = new String[rentals.size()][10]; //Se le pasa la lista de peliculas crea la matriz y hace una fila por cada movie  y los espacios para la cantidad de atributos q son 6

        for (int i= 0; i< rentals.size(); i++) {

            Rental ren = rentals.get(i);
            matrix[i][0] = ren.getUser().getUserName(); 
            matrix[i][1] = ren.getUser().getUserid();
            matrix[i][2] = String.valueOf(ren.getUser().getUserState());

            matrix[i][3] = ren.getCustomer().getCustomerName(); 
            matrix[i][4] = ren.getCustomer().getCustomerId(); 
            matrix[i][5] = String.valueOf(ren.getCustomer().getCustomerState());

            matrix[i][6] = ren.getRentalId();
            matrix[i][7] = String.valueOf(ren.getRentalState());
            matrix[i][8] = String.valueOf(ren.getRentalDays());
            matrix[i][8] = String.valueOf(ren.getRentalCost());
        }
        return matrix; 
    }
    public static boolean saveRentalMToCSV(String filename, String[][] matrix) {
        StringBuilder sb= new StringBuilder(); //sirve pa construir strings eficientemente

        for (int i = 0; i <matrix.length; i++) {
            for (int j = 0; j <matrix[i].length; j++) { //recorre la matriz

                sb.append(matrix[i][j]); //agrega el dato actual al texto

                if (j < matrix[i].length - 1) {
                    sb.append(","); //lo va separando por comas
                }
            }
            sb.append(System.lineSeparator());// agrega salto de linea para que la sig fila empiece abajo
        }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) { //abre el archivo y le mete todo lo que se construyo con sb
        bw.write(sb.toString());
        return true;

    } catch (IOException e) {
        System.err.println("Error guardando CSV... " + e.getMessage()); //si explota sale msj 
        return false;
    }
}
    public static String[][] loadCSVToM(String filename) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return null;
        }
        String[] rows = sb.toString().split(System.lineSeparator());
        String[][] matrix = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }
        return matrix;
    }
    public static ArrayList<Rental> matrixToRentals(String[][] matrix, Store store) {
        ArrayList<Rental> rentals = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String userName = matrix[i][0];
            String userId = matrix[i][1];
            boolean userState = Boolean.parseBoolean(matrix[i][2]);

            String customerName = matrix[i][3];
            String customerId = matrix[i][4];
            boolean customerState = Boolean.parseBoolean(matrix[i][5]);

            String rentalId = matrix[i][6];
            boolean rentalActive = Boolean.parseBoolean(matrix[i][7]);
            int rentalDays = Integer.parseInt(matrix[i][8]);
            float rentalCost = Float.parseFloat(matrix[i][9]);

            User user = new User(userName, userId);
            Customer customer = store.findCustomerById(customerId);

            if (customer == null) {
                customer = new Customer(customerName, customerId);
                if (customerState) {
                    customer.changeCustomerState();
                }
            }
            ArrayList<Movie> movies = new ArrayList<>();
            Rental ren = new Rental(movies, user, customer, rentalId,rentalDays);
            rentals.add(ren);
        }
        return rentals;
    }
    
//Store
    public static String[][] exportStoreToM(Store store) {

        ArrayList<Rental> rentals = store.consultAllRentals();
        String[][] matrix = new String[rentals.size()][10];

        for (int i = 0; i < rentals.size(); i++) {

            Rental ren = rentals.get(i);
            User user = ren.getUser();
            Customer customer = ren.getCustomer();

            matrix[i][0] = user.getUserName();
            matrix[i][1] = user.getUserid();
            matrix[i][2] = String.valueOf(user.getUserState());

            matrix[i][3] = customer.getCustomerName();
            matrix[i][4] = customer.getCustomerId();
            matrix[i][5] = String.valueOf(customer.getCustomerState());

            matrix[i][6] = ren.getRentalId();
            matrix[i][7] = String.valueOf(ren.getRentalState());
            matrix[i][8] = String.valueOf(ren.getRentalDays());
            matrix[i][9] = String.valueOf(ren.getRentalCost());
        }
        return matrix;
    }
    //este metodo es un tris diferente
    public static boolean saveStoreMToCSV(String filename, String[][] matrix) { 

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {

                sb.append(matrix[i][j]);

                if (j < matrix[i].length - 1) {
                    sb.append(",");
                }
            }

            sb.append(System.lineSeparator());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(sb.toString());
            return true;

        } catch (IOException e) {
            System.err.println("Error guardando CSV... " + e.getMessage());
            return false;
        }
    }
    public static String[][] loadCSVToMStore(String filename) {

        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return null;
        }
        String[] rows = sb.toString().split(System.lineSeparator());

        String[][] matrix = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }

        return matrix;
    }
    public static Store matrixToStore(String[][] movieMatrix, String[][] customerMatrix, String[][] genreMatrix, String[][] rentalMatrix) {
        ArrayList<Genre> genres = matrixToGenres(genreMatrix, null);

            Store store = new Store("Store", 0,new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),genres);

            ArrayList<Customer> customers =
            matrixToCustomer(customerMatrix);

            ArrayList<Movie> movies =
            matrixToMovies(movieMatrix, store);

            Store storeFinal = new Store("Store", 0,movies,customers, new ArrayList<>(),genres);

            ArrayList<Rental> rentals =
            matrixToRentals(rentalMatrix, storeFinal);

            return new Store("Store",0,movies,customers,rentals,genres);
    }

//User
    public static String[][] exportUserToM(ArrayList<User> users) {

        String[][] matrix = new String[users.size()][3];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            matrix[i][0] = user.getUserName();
            matrix[i][1] = user.getUserid();
            matrix[i][2] = String.valueOf(user.getUserState());
        }
        return matrix;
    }
    public static boolean saveUserMToCSV(String filename, String[][] matrix) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                sb.append(matrix[i][j]);

                if (j < matrix[i].length - 1) {
                    sb.append(",");
                }
            }

            sb.append(System.lineSeparator());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            bw.write(sb.toString());
            return true;

        } catch (IOException e) {

            System.err.println("Error guardando CSV... " + e.getMessage());
            return false;
        }
    }
    public static String[][] loadCSVToMUser(String filename) {

        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {

            System.err.println("Error reading CSV: " + e.getMessage());
            return null;
        }
        String[] rows = sb.toString().split(System.lineSeparator());
        String[][] matrix = new String[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }

        return matrix;
    }
    public static ArrayList<User> matrixToUsers(String[][] matrix) {

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String userName = matrix[i][0];
            String userId = matrix[i][1];
            boolean userState = Boolean.parseBoolean(matrix[i][2]);

            User user = new User(userName, userId);

            users.add(user);
        }

        return users;
    }
//haber si esta gonorreota por fin da
}
