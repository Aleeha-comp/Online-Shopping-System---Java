package onlineshoppingsystem;

import java.io.*;
import java.util.*;

public class FileHandling {

    // ---------------- SAVE GENERIC DATA ----------------
    public static <T> void saveData(String fileName, ArrayList<T> list) {

        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(list);
            out.close();
            System.out.println("Saved successfully: " + fileName);
        } 
        
        catch (FileNotFoundException fn) {
            System.out.println("File not found.");
        }

        catch (IOException e) {
            System.out.println("Error saving " + fileName + ": " + e.getMessage());
        }
    }

    // ---------------- LOAD GENERIC DATA ----------------
    public static <T> ArrayList<T> loadData(String fileName) {

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            ArrayList<T> arraylist = (ArrayList<T>) in.readObject();
            
            in.close();
            return arraylist;
        } 
        
        catch (FileNotFoundException e) {
            System.out.println(fileName + " not found. Returning empty list.");
        } 

        catch (EOFException eof) {
            System.out.println("End of File!");
        }

        catch (IOException e) {
            System.out.println("Error reading " + fileName + ": " + e.getMessage());
        } 
        
        catch (ClassNotFoundException e) {
            System.out.println("Class error: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
