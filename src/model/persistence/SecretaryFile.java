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

    private File secretaryDataBaseFile = new File("data/SecretaryUsers.txt");

    public SecretaryFile() {
        try {
            if (!secretaryDataBaseFile.exists()) {
                secretaryDataBaseFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creando el archivo de Secretaría: " + e.getMessage());
        }
    }

    public boolean readSecretaryDataBase(User user) {
        String hit = user.getID();
        try (BufferedReader reader = new BufferedReader(new FileReader(secretaryDataBaseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",",2);
                if (fields.length == 2 && fields[0].equals(hit)) {
                    user.setUserType(fields[1]);
                    user.setIsAdmin(fields[1].equalsIgnoreCase("admin"));
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo base de Secretaría: " + e.getMessage());
        }
        return false;
    }
}


