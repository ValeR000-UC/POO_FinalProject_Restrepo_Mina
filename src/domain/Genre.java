package domain;
import java.io.Serializable;

public class Genre implements Serializable { 
    private static final long serialVersionUID = 1L;

    private String genreName;
    private String genreId;

    public Genre(String genreName, String genreId) {

        this.genreName = genreName;
        this.genreId = genreId;
    }

    public String getGenreName() {

        return genreName;
    }

    public String getGenreId() {

        return genreId;
    }
}