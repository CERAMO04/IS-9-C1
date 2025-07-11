package model.persistence;

import model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class UserFile {
 
    private File systemUserDataBAse = new File("data/SystemUsers.txt");


    public UserFile(){
        try {
            if (!systemUserDataBAse.exists()) {
                systemUserDataBAse.createNewFile(); 
            }
        } catch (Exception e) {
            System.out.println("Error creando el archivo de base de dato del comedor: " + e.getMessage());
        }
    }
    public void saveUser(User curretUser){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(systemUserDataBAse, true))){
            String line = curretUser.getID() + "," +
                          curretUser.getUserType() + "," +
                          curretUser.getName() + "," +
                          curretUser.getLastName() + "," +
                          curretUser.getEmail() + "," +
                          curretUser.getUser() + "," +
                          curretUser.getPassword() + "," +                          
                          curretUser.getIsAdmin() + "\n";
            writer.write(line);
        }catch (Exception e) {
            System.out.println("Error escribiendo en el archivo: " + e.getMessage());
        }
    }
    public boolean userExists(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", 2);
                if (fields.length > 0 && fields[0].trim().equals(id.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error verificando existencia de usuario:" + e.getMessage());
        }
        return false;
    }
}
