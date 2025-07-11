package model.persistence;

import model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;


public class SecretaryFile {

    private File secretaryDataBaseFile = new File("data/SystemUsers.txt");

    public SecretaryFile() {
        try {
            if (!secretaryDataBaseFile.exists()) {
                secretaryDataBaseFile.createNewFile(); // Asegura existencia del archivo
            }
        } catch (IOException e) {
            System.out.println("Error creando el archivo de Secretaría: " + e.getMessage());
        }
    }

    public boolean readSecretaryDataBase(int ID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(secretaryDataBaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", 2); // solo separa en dos partes
                if (fields[0].equals(ID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo base de Secretaría: " + e.getMessage());
        }
        return false;
    }
}


