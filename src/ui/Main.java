package ui;
import java.util.ArrayList;

import domain.Movie;
import domain.Store;
import domain.User;

public class Main {

    public static void main(String[] args) {

        Ui ui = new UiConsole();
        ArrayList<Movie> movies;
        User user = new User("Sebastian Mina", "1062280136", true);
        Store store = new Store(); // crear listas.
        MainMenu main = new MainMenu(store, ui);
        main.start();

        
    }
}