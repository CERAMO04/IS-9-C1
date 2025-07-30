package model.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MenuFile {

    private File file;

    public MenuFile() {
        file = new File("data/MenuFile.txt");
    }

    public String[] readMenu() {
        String[] result = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            result[0] = reader.readLine();
            result[1] = reader.readLine(); 
        } catch (IOException e) {
            System.out.println("Error leyendo el menú diario: " + e.getMessage());
        }
        return result;
    }
    public void SaveDailyMenu(){

    }
}