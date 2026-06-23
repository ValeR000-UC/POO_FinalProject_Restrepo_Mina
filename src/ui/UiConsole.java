package ui;

import java.util.Scanner;

public class UiConsole implements Ui {
    
    @Override
    public String inputText(String title){

        Scanner sc = new Scanner(System.in);
        System.out.print(title);

        return sc.nextLine(); // Retornamos lo que el usuario haya escrito (Para guardarlo en la variable correspondiente)

    }

     @Override
    public void showText(Object object){

        System.out.println(object); // Imprimimos

    }
}
