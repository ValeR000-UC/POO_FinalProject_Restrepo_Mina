package data;
import java.io.*;

//Unlike typical persistence approaches, this system serializes the complete Store object rather than individual entities, since Store already contains all system collections. 
//Handles binary persistence using ObjectOutputStream and ObjectInputStream, generating a single store.dat file that preserves the entire application state.

public class DataManager{
    //saveObject, Saves a serializable object to a file
    public static <T extends Serializable> boolean saveObject(String filename, T object) {  // Serializes and saves an object to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) { //Opens file and prepares object serialization
            oos.writeObject(object); // Writes the object into the file
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el objeto: " + e.getMessage());
            return false; // Returns false if saving fails
        }
    }

    //loadObject, Loads a serialized object from a file
    @SuppressWarnings("unchecked")  // Suppresses warning because Java cannot verify cast to generic type T at compile time
    public static <T extends Serializable> T loadObject(String filename) { // Loads and deserializes an object from a file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {  // Opens file and enables object reading
            return (T) ois.readObject();// Reads and casts the object to type T
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el objeto: " + e.getMessage());
            return null; // Returns null if loading fails
        }
    }
}


