package ui;
//In this case, the SC is not closed because it is used to read everything the user types in the console. 
//Creating a new SC every time input is needed creates unnecessary objects that all read from the same place. 
//Also, if one Scanner is closed using close(), the keyboard input is closed too, 
//and the program will not be able to ask for more input even if another `Scanner` is created later. 
// That is why a single SC is created as a class attribute and reused during the whole program execution.


import java.util.Scanner;

public class UiConsole implements Ui {

    private Scanner sc = new Scanner(System.in); // reused scanner

    @Override
    public String inputText(String title){

        System.out.print(title);

        return sc.nextLine(); // returns user input
    }

    @Override
    public void showText(Object object){

        System.out.println(object);
    }
}
