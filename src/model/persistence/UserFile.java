package model.persistence;

import model.User;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

public class UserFile {
    
    private File systemUserDataBAse = new File("data/SystemUsers.txt");

    // Para tests
    protected void setSystemUserDatabase(File file) {
    this.systemUserDataBAse = file;
    }
    
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
                          curretUser.getIsAdmin() + "," +
                          curretUser.getWallet().getBalance() + "\n"; 
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

    public boolean userExists(String userName, String userPassword){
        try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", 9);
                if (fields.length == 9) {
                    String userAux = fields[5];
                    String passWordAux = fields[6];
                    if(userAux.equals(userName.trim()) && passWordAux.equals(userPassword.trim())) {
                        String ID = fields[0];
                        String userType = fields[1];
                        String name = fields[2];
                        String lastName = fields[3];
                        String email = fields[4];
                        String isAdmin = fields[7];
                        String value = fields[8];
                        double cash = Double.parseDouble(value);

                        User.clearInstance();
                        User.init(name, lastName, ID, email, passWordAux, userAux, cash, userType);
                        User curretUser = User.getInstance();
                        curretUser.setIsAdmin(isAdmin.equals("true"));

                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error verificando existencia de usuario:" + e.getMessage());
        }
        return false;
    }
    // Funcion principalmente usada para tests
    public boolean validateFullUser(User inputUser) {
    try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",", -1);
            if (fields.length == 9) {
                String email = fields[4].equals("null") ? null : fields[4];
                
                User.clearInstance();
                User.init(
                    fields[2],  // name
                    fields[3],  // lastName
                    fields[0],  // ID
                    email,      // email
                    fields[6],  // password
                    fields[5],  // username
                    Double.parseDouble(fields[8]), // balance
                    fields[1]   // userType
                );
                User storedUser = User.getInstance();
                storedUser.setIsAdmin(Boolean.parseBoolean(fields[7]));

                if (Objects.equals(inputUser.getID(), storedUser.getID()) &&
                    Objects.equals(inputUser.getName(), storedUser.getName()) &&
                    Objects.equals(inputUser.getLastName(), storedUser.getLastName()) &&
                    Objects.equals(inputUser.getEmail(), storedUser.getEmail()) && 
                    Objects.equals(inputUser.getUser(), storedUser.getUser()) &&
                    Objects.equals(inputUser.getPassword(), storedUser.getPassword()) &&
                    Objects.equals(inputUser.getUserType(), storedUser.getUserType()) &&
                    inputUser.getIsAdmin() == storedUser.getIsAdmin() &&
                    Math.abs(inputUser.getWallet().getBalance() - storedUser.getWallet().getBalance()) < 0.001) {
                    return true;
                }
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error de validacion: " + e.getMessage());
    }
    return false;
    }
    public void saveNewBalance(double amount){
        User user = User.getInstance();
        File inputFile = systemUserDataBAse;
        File tempFile = new File(systemUserDataBAse.getPath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", 9);
                if (fields.length == 9) {
                    if(fields[5].equals(user.getUser()) && fields[6].equals(user.getPassword())){
                        fields[8] = String.valueOf(amount);
                        line = String.join(",", fields);
                    }
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error actualizando saldo del usuario: " + e.getMessage());
        }
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error al renombrar archivo temporal.");
            }
        } else {
            System.out.println("Error al eliminar archivo original.");
        }
    }
}
