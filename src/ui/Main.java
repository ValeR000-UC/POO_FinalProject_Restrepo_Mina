package ui;
import java.util.ArrayList;

import domain.Movie;
import domain.Store;
import domain.User;
import domain.Movie;
import domain.Customer;
import domain.Rental;
import domain.Genre;
import data.DataManager;
import data.CSVmanager;

public class Main {

    public static void main(String[] args) {

        Ui ui = new UiConsole();

        // Try to load existing store from file
        Store store = DataManager.loadObject("store.dat");

        // If no file exists, create a new store from scratch
        if (store == null) {

            User user = new User("Sebastian Mina", "1062280136", true);

            // Load genres from preexisting CSV
            ArrayList<Genre> genres = new ArrayList<>();
            String[][] genreMatrix = CSVmanager.loadCSVToMGenre("genres.csv");
            if (genreMatrix != null) {
                genres = CSVmanager.matrixToGenres(genreMatrix, null);
            }

            store = new Store(
                "VIDEO STORE",
                7,
                new ArrayList<Movie>(),
                new ArrayList<Customer>(),
                new ArrayList<Rental>(),
                genres,
                user
            );
        }

        MainMenu main = new MainMenu(store, ui);
        main.start();

        // Save store state on exit
        DataManager.saveObject("store.dat", store);
    }
}