package ui;
import domain.Store;
import domain.User;

public class Main {

    public static void main(String[] args) {

        Ui ui = new UiConsole();
        User user = new User("Sebastian Mina", "1062280136");
        Store store = new Store("VIDEO STORE", user, 7);
        MainMenu main = new MainMenu(store, ui);
        main.start();

        
    }
}