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
    //Matriz a lista de objetos

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
    //Genre
    //Rental
    //Store
    //User
    
}
