package ui;

public interface Ui {
    
    // Abstract method, it will be implemented in each class.
    // Used to receive text input (Strings)
    public abstract String inputText(String title); 

    // Method to read integers (receives text and converts it to int)
    public default int inputInt(String title){
        return Integer.parseInt(inputText(title));
    }
    // Method to read floats (receives text and converts it to float)
    public default float inputFloat(String title){
        return Float.parseFloat(inputText(title));
    }

    // Prints a blank line
    public default void newLine(){
        System.out.println();
    }

    // Method to display output
    public abstract void showText(Object object);
}
