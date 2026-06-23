package data;
import domain.*; 
import java.io.*;
import java.util.ArrayList;

//Handles CSV-based data exchange for every entity in the system. For each class, four methods manage the full cycle: 
//converting an object list into a string matrix, writing that matrix to a CSV file, reading the CSV file back into a matrix, 
//and reconstructing the object list from that matrix. 
//Supports movies, customers, genres, rentals, users and Store as a complete reconstructed object.

public class CSVmanager {


// ---- Movie ----
    //Object List(Movie) → Matrix
    public static String[][] exportMovieToM(ArrayList<Movie> movies) { 
    String[][] matrix = new String[movies.size()][7]; //Creates a row per movie and 7 columns for each attribute

    for (int i= 0; i< movies.size(); i++) { //iterates through the movie list

        Movie mov= movies.get(i); //gets the movie at position i
        matrix[i][0] = mov.getMovieName(); //row i, column 0: movie name
        matrix[i][1] = mov.getMovieId();
        matrix[i][2] = String.valueOf(mov.isMovieRented()); // converts boolean to String since matrix only holds Strings
        matrix[i][3] = mov.getMovieYear();
        matrix[i][4]= mov.getMovieDirector();

        matrix[i][5]= mov.getMovieGenre().getGenreName();
        matrix[i][6]= mov.getMovieGenre().getGenreId();
    }
    return matrix;
}
    //Matrix → CSV
    public static boolean saveMoviesMToCSV(String filename, String[][] matrix) {

    StringBuilder sb= new StringBuilder();  //efficiently builds the CSV string piece by piece

    for (int i = 0; i <matrix.length; i++) {
        for (int j = 0; j <matrix[i].length; j++) {  // iterates through each column of the row

            sb.append(matrix[i][j]); // appends current cell value to the string (Sb)

            if (j < matrix[i].length - 1) {
                sb.append(","); // adds comma between values, skips it on the last column to avoid trailing comma
            }
        }
        sb.append(System.lineSeparator());//adds a line break after each row
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) { // opens or creates the file and writes everything built in sb
        bw.write(sb.toString()); // writes the full CSV string to the file
        return true; //file saved successfully

    } catch (IOException e) {
        System.err.println("Error guardando CSV... " + e.getMessage());  // prints error if something goes wrong
        return false;
    }
}
    //CSV → Matrix
    public static String[][] loadCSVToMMovies(String filename) {

    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());  // reads line by line and builds the full file content
        }
    } catch (IOException e) {
        System.err.println("Error reading CSV: " + e.getMessage());
        return null;
    }
    String[] rows = sb.toString().split(System.lineSeparator()); //splits content into individual rows
    String[][] matrix = new String[rows.length][];

    for (int i = 0; i < rows.length; i++) {
        matrix[i] = rows[i].split(","); //splits each row by comma to get individual values
    }
    return matrix;
}
    //Matrix → Object List
    public static ArrayList<Movie> matrixToMovies(String[][] matrix, Store store) {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String name = matrix[i][0]; // extracts each attribute from its column
            String id = matrix[i][1];
            boolean rented = Boolean.parseBoolean(matrix[i][2]);  // converts String back to boolean
            String year = matrix[i][3];
            String director = matrix[i][4];

            String genreName= matrix[i][5];
            String genreId = matrix[i][6];

            Genre genre = store.findGenreById(genreId); //looks up the genre object from the store
            if (genre == null) {
                continue; // skips movie if genre does not exist in the system
            }
            Movie mov = new Movie(name, id, year, director, genre);
            if (rented) {
                mov.markAsRented();  //restores rented state if it was rented when exported
            }
            movies.add(mov);
        }
        return movies;
}

// ---- Customer ----
    public static String[][] exportCustomerToM(ArrayList<Customer> customers) {
    String[][] matrix = new String[customers.size()][3]; 

    for (int i= 0; i< customers.size(); i++) {

        Customer cus= customers.get(i); 
        matrix[i][0] = cus.getCustomerName(); 
        matrix[i][1] = cus.getCustomerId();
        matrix[i][2] = String.valueOf(cus.getCustomerState());
    }
    return matrix; 
}
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
    public static ArrayList<Customer> matrixToCustomer(String[][] matrix) {
        ArrayList<Customer> customers = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            String customerName = matrix[i][0];
            String customerId = matrix[i][1];
            boolean customerState = Boolean.parseBoolean(matrix[i][2]);

            Customer cus = new Customer(customerName, customerId);// creates customer with base state (inactive by default)
            if (customerState) {
                cus.changeCustomerState(); //restores active state if it was active when exported
            }
            customers.add(cus);
        }
        return customers;
    }

// ---- Genre ----
    public static String[][] exportGenreToM(ArrayList<Genre> genres) {
        String[][] matrix = new String[genres.size()][2];
        for (int i= 0; i< genres.size(); i++) {

            Genre gen = genres.get(i);
            matrix[i][0] = gen.getGenreName(); 
            matrix[i][1] = gen.getGenreId();
        }
        return matrix; 
    }
    public static boolean saveGenreMToCSV(String filename, String[][] matrix) {
        StringBuilder sb= new StringBuilder();

        for (int i = 0; i <matrix.length; i++) {
            for (int j = 0; j <matrix[i].length; j++) {

                sb.append(matrix[i][j]); 

                if (j < matrix[i].length - 1) {
                    sb.append(",");
                }
            }
            sb.append(System.lineSeparator());
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
    
// ---- Rental ----
    public static String[][] exportRentalToM(ArrayList<Rental> rentals) {
        String[][] matrix = new String[rentals.size()][10]; 
        for (int i= 0; i< rentals.size(); i++) {

            Rental ren = rentals.get(i);
            matrix[i][0] = ren.getUser().getUserName(); // accesses nested User object to get its attributes
            matrix[i][1] = ren.getUser().getUserid();
            matrix[i][2] = String.valueOf(ren.getUser().getUserState());

            matrix[i][3] = ren.getCustomer().getCustomerName(); // accesses nested Customer object to get its attributes
            matrix[i][4] = ren.getCustomer().getCustomerId(); 
            matrix[i][5] = String.valueOf(ren.getCustomer().getCustomerState());

            matrix[i][6] = ren.getRentalId();
            matrix[i][7] = String.valueOf(ren.getRentalState());
            matrix[i][8] = String.valueOf(ren.getRentalDays());
            matrix[i][9] = String.valueOf(ren.getRentalCost()); //saves cost as a fixed snapshot, not recalculated on import
        }
        return matrix; 
    }
    public static boolean saveRentalMToCSV(String filename, String[][] matrix) {
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
            int rentalCost = Integer.parseInt(matrix[i][9]);

            User user = new User(userName, userId, userState);
            Customer customer = store.findCustomerById(customerId);// tries to find existing customer in store

            if (customer == null) {
                customer = new Customer(customerName, customerId); // if not found, recreates it from CSV data
                if (customerState) {
                    customer.changeCustomerState();
                }
            }

            ArrayList<Movie> movies = new ArrayList<>();
            Rental ren = new Rental(movies, user, customer, rentalId, rentalDays);
            ren.setRentalState(rentalActive); // siempre setea, no solo si es true — si era false quedaba true por defecto
            ren.setRentalCost(rentalCost);
            rentals.add(ren);
        }
        return rentals;
    }
    
// ---- Store ----
    public static String[][] exportStoreToM(Store store) {

        ArrayList<Rental> rentals = store.consultAllRentals(); // gets all rentals directly from the store object
        String[][] matrix = new String[rentals.size()][10];

        for (int i = 0; i < rentals.size(); i++) {

            Rental ren = rentals.get(i);
            User user = ren.getUser(); //extracts nested objects first to avoid repeated method calls
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
    public static Store matrixToStore(String[][] movieMatrix, String[][] customerMatrix,String[][] genreMatrix,String[][] rentalMatrix) {

        ArrayList<Genre> genres = matrixToGenres(genreMatrix, null); // genres first, they depend on nothing

        //base store with only genres, needed for movie reconstruction
        Store store = new Store("Store", 0,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),genres,null);

        ArrayList<Movie> movies = matrixToMovies(movieMatrix, store); // movies need genres from store to link correctly
        ArrayList<Customer> customers = matrixToCustomer(customerMatrix);

        // intermediate store with movies and customers, needed so rentals can find them by ID
        Store tempStore = new Store("Store",0,movies,customers, new ArrayList<>(),genres,null);

        ArrayList<Rental> rentals = matrixToRentals(rentalMatrix, tempStore);//rentals need both movies and customers already reconstructed

        return new Store("Store",0,movies,customers,rentals,genres,null); //final store with everything assembled
    }

// ---- User ----
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

            User user = new User(userName, userId, userState);

            users.add(user);
        }

        return users;
    }
}
