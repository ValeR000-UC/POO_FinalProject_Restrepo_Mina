public class Movie {
    
    // Atributes
    private String movieName;
    private String movieId;
    private boolean movieRented = false; // Todas se crean en false por defecto.
    private String year;
    private String director;
    private Genre genre;

    // Constructor
    Movie(String movieName, String movieId, String year, String director, Genre genre) {

        this.movieName = movieName;
        this.movieId = movieId;
        this.year = year;
        this.director = director;
        this.genre = genre;

    }

    // getters
    public String getMovieName() {

        return movieName;
    } 

    public String getMovieId() {

        return movieId;
    }

    public String getMovieYear() {

        return year;
    }

    public String getMovieDirector() {

        return director;
    }

    public Genre getMovieGenre() {

        return genre;
    }

    public boolean isMovieRented() {

        return movieRented;
    }

    public boolean markAsRented() {

        if (isMovieRented() == false) { // Preguntamos si la pelicula no esta rentada, para poder rentarla.

                movieRented = true; // Queda rentada.
                return true; // Avisamos en el main que todo salio correcto.

            }

        return false; // Avisamos en el main que NO se pudo rentar la pelicula.
    }

    public boolean markAsAvaible() {

        if (isMovieRented() == true) { // Preguntamos si la pelicula esta rentada, para poder devolverla.

                movieRented = false; // Queda disponible.
                return true; // Avisamos en el main que todo salio correcto.


                // Toca corregir este evento porque en la clase rental, 
                // Se supone que dentro del for va a estar retornando booleanos.
            }

        return false; // Avisamos en el main que NO se pudo rentar la pelicula. 
    }
}
