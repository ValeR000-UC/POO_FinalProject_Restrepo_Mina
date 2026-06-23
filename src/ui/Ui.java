package ui;

    public interface Ui {
        
        // Metodo abstracto, lo reescribimos en cada clase.
        // Este, es para recibir entradas de texto (Strings)
        public abstract String inputText(String title); 

        // Metodo para leer enteros (Recibimos texto y guardamos enteros)
        public default int inputInt(String title){

            return Integer.parseInt(inputText(title));
        }

        // Metodo para leer flotantes (Recibimos texto y guardamos flotntes)
        public default float inputFloat(String title){

            return Float.parseFloat(inputText(title));
        }

        public default void newLine(){

            System.out.println();

        }

        // Metodo para imprimir.
        public abstract void showText (Object object);

        

    

}