package model.persistence;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class MenuFile {

    private File file;

    public MenuFile() {
        file = new File("data/MenuFile.txt");
    }

    public void SaveDailyMenu(String breakfast, String lunch) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Desayuno");
            writer.newLine();
            writer.write(breakfast.trim());
            writer.newLine();
            writer.write("Almuerzo");
            writer.newLine();
            writer.write(lunch.trim());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] readMenu() {
        StringBuilder breakfast = new StringBuilder();
        StringBuilder lunch = new StringBuilder();
        boolean isLunch = false;
        boolean isBreakfast = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("Desayuno")) {
                    isBreakfast = true;
                    isLunch = false;
                    continue;
                } else if (line.trim().equalsIgnoreCase("Almuerzo")) {
                    isLunch = true;
                    isBreakfast = false;
                    continue;
                }

                if (isBreakfast) breakfast.append(line).append("\n");
                else if (isLunch) lunch.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[] {
            breakfast.toString().trim(), // índice 0: desayuno
            lunch.toString().trim()      // índice 1: almuerzo
        };
    }
}