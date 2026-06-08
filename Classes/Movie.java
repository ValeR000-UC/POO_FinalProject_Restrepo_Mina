public class Movie {
    
    // Atributes
    private String movieName;
    private String movieId;
    private boolean movieRented = false;
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

    public boolean isMovieAvaible() {

        return movieRented;
    }

}
