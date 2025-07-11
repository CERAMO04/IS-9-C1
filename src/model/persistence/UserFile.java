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
                          curretUser.getUser() + "," +
                          curretUser.getPassword() + "," +
                          curretUser.getName() + "," +
                          curretUser.getLastName() + "," +
                          curretUser.getEmail() + "," +
                          curretUser.getIsAdmin();
            writer.write(line);
        }catch (Exception e) {
            System.out.println("Error escribiendo en el archivo: " + e.getMessage());
        }
    }
    public void checkAndSaveUser(User user){
        SecretaryFile secretaryDataBase = new SecretaryFile();
        if (secretaryDataBase.readSecretaryDataBase(user)) {
            saveUser(user);
        }else{
            System.out.println("El usuario No existe en la base de datos de la secretaria.");
        }
    }

}
