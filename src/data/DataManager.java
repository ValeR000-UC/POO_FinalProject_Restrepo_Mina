package data;
import java.io.*; //el .io. en java corresponde a todas las herramientas que sirven para leer y cargar los daticos

//Nota: como store tiene todas las listas con la info que recolecta en consola entonces nosotros NO vamos a hacer como hizo cesitar
//ni como normalmente se hace por que como tenemos una clase que ya lo tiene todo, lo que vamos a hacer es estar cargando y guardando
//toda esa clase. eso va a ser la persistencia de archivos

public class DataManager{
    public static <T extends Serializable> boolean saveObject(String filename, T object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(object);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el objeto: " + e.getMessage());
            return false;
        }
    }
    //<T extends Serializable> → declara que este método trabaja con un tipo genérico T, que tiene que poder serializarse.
    //(String filename, T object) → recibe el nombre del archivo y el objeto que querés guardar.
    //FileOutputStream(filename) → abre/crea el archivo físico para escribir bytes en él
    //ObjectOutputStream(...) → esta instruccion hace que se pueda escribir objetos completos, no solo bytes sueltos.
    //try (...) → es un try-with-resources: al terminar el bloque cierra el archivo automáticamente solo
    //oos.writeObject(object); Acá pasa la magia: agarra tu objeto y lo escribe (serializado) en el archivo.
    //si no explota nada devuelve true y si no saca mensaje de error y devuelve false

    // Metodo GENERICO para cargar cualquier objeto serializable
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T loadObject(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el objeto: " + e.getMessage());
            return null;
        }
    }
    //public static <T extends Serializable> T loadObject(String filename) {Igual que antes, pero ahora el método devuelve un T (el objeto que cargó), no un boolean.
    //FileInputStream(filename) → abre el archivo para leer bytes. 
    //ObjectInputStream(...) → le agrega la capacidad de leer objetos completos desde esos bytes.
    //ois.readObject() → lee el archivo y reconstruye el objeto guardado (pero Java lo devuelve como tipo Object genérico).
    //(T) → lo "castea" (convierte) al tipo que vos le pediste cuando llamás al método (ej: Persona).
    //si explota devuelve null y saca msj

    //Guardar: Persona → se convierte a bytes → se escribe en un archivo .dat.
    //Cargar: archivo .dat → se leen los bytes → se reconstruye la Persona original, tal cual estaba.
}


