package domain;
import java.io.Serializable;

//Represents a movie in the store.
// Holds attributes such as name, ID, year, director and genre. Tracks whether the movie is currently rented or available.

public class Movie implements Serializable { 
    // Implements Serializable so the class can be converted into bytes this is used to save objects into files or send them through streams.
    // Serialization means converting an object into bytes so it can be stored or transmitted.

    private static final long serialVersionUID = 1L;

    // Atributes
    private String movieName;
    private String movieId;
    private boolean movieRented = false; // All movies are created with false by default (not rented)
    private String year;
    private String director;
    private Genre genre;

    // Constructor
    public Movie(String movieName, String movieId, String year, String director, Genre genre) {

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

    public void markAsRented() {
        if (isMovieRented() == false) { // Check if the movie is not rented, so it can be rented

                movieRented = true; // Mark the movie as rented
            }
    }

    public void markAsAvaible() {
        if (isMovieRented() == true) { // Check if the movie is rented, so it can be returned.

                movieRented = false; // Mark the movie as available.

            }
    }
}
