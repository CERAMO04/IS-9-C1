package model.persistence;

import model.User;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


public class SecretaryFile {

    private File secretaryDataBaseFile = new File("data/SecretaryUsers.txt");

    public File getSecretaryDatabaseFile() {
        return this.secretaryDataBaseFile;
    }

    // Para tests
    public void setSecretaryDataBaseFile(File file) {
        this.secretaryDataBaseFile = file;
    }

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
                String[] fields = line.split(",",7);
                //Investigar
                
                if (fields.length == 6 && fields[0].equals(hit)) {
                    user.setUserType(fields[1]);
                    user.setName(fields[2]);
                    user.setLastName(fields[3]);
                    user.setEmail(fields[4]);
                    user.setImgae(fields[5]);
                    user.createWallet();
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo base de Secretaría: " + e.getMessage());
        }
        return false;
    }
}


