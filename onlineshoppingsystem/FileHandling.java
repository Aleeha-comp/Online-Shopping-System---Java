
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

            file.close();

            System.out.println("Saved successfully: " + fileName);
        }

        catch (IOException e) {

            System.out.println("Error saving " + fileName + ": " + e.getMessage());
        }
    }

    // ---------------- LOAD GENERIC DATA ----------------
    @SuppressWarnings("unchecked")

    public static <T> ArrayList<T> loadData(String fileName) {

        try {

            File file = new File(fileName);

            // FILE DOES NOT EXIST
            if (!file.exists()) {
                return new ArrayList<>();
            }

            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInput);

            ArrayList<T> list = (ArrayList<T>) in.readObject();

            in.close();

            fileInput.close();

            return list;
        }

        catch (EOFException e) {

            return new ArrayList<>();
        }

        catch (Exception e) {
            System.out.println("Error loading " + fileName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}