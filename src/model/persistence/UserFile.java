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

    public void saveUser(User curretUser){
        File systemUserFile = new File("data/SystemUser.txt");
        try {        
            if (!systemUserFile.exists()) systemUserFile.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(systemUserFile, true));
            
            String line = curretUser.getName() + "," +
                          curretUser.getLastName() + "," +
                          curretUser.getID() + "," +
                          curretUser.getUser() + "," +
                          curretUser.getPassword() + "," +
                          curretUser.getEmail() + "," +
                          curretUser.getUserType() + "," +
                          curretUser.getIsAdmin() + ".";
            writer.write(line);
            writer.close();

        } catch (Exception e) {
            System.out.println("Error escribiendo en el archivo: " + e.getMessage());
        }
    }
    public void IsINSecretaryDataBase(User user){
        SecretaryFile secretaryDataBase = new SecretaryFile();
        if (secretaryDataBase.readSecretaryDataBase(user.getID())) {
            saveUser(user);
        }else{
            System.out.println("El usuario No existe en la base de datos de la secretaria.");
        }
    }

}
