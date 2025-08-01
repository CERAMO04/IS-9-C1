package model.persistence;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import model.User;
import model.UserData;
import utils.ImageUtils;

public class UserFile {
    
    private File systemUserDataBAse = new File("data/SystemUsers.txt");

    // Para tests
    public void setSystemUserDatabase(File file) {
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
    public void saveUser(User currentUser){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(systemUserDataBAse, true))){
            String line = currentUser.getID() + "," +
                          currentUser.getUserType() + "," +
                          currentUser.getName() + "," +
                          currentUser.getLastName() + "," +
                          currentUser.getEmail() + "," +
                          currentUser.getUser() + "," +
                          currentUser.getPassword() + "," +                          
                          currentUser.getIsAdmin() + "," +
                          currentUser.getIsSuperSu() + "," +
                          currentUser.getWallet().getBalance() + "," +
                          currentUser.getImage() + "\n"; 
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
                String[] fields = line.split(",", 11);
                if (fields.length == 11) {
                    String userAux = fields[5];
                    String passWordAux = fields[6];
                    if(userAux.equals(userName.trim()) && passWordAux.equals(userPassword.trim())) {
                        String ID = fields[0];
                        String userType = fields[1];
                        String name = fields[2];
                        String lastName = fields[3];
                        String email = fields[4];
                        String isAdmin = fields[7];
                        String isSuperSu = fields[8];
                        String value = fields[9];
                        String image = fields[10];
                        double cash = Double.parseDouble(value);

                        User.clearInstance();
                        User.init(name, lastName, ID, email, passWordAux, 
                                    userAux, cash, userType,image,Boolean.parseBoolean(isSuperSu),
                                    Boolean.parseBoolean(isAdmin));
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error verificando existencia de usuario:" + e.getMessage());
        }
        return false;
    }
    public boolean isImageInUserBase(BufferedImage userImage) {
        try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 11) continue;

                String imageName = fields[10].trim();
                String[] extensions = {".jpg", ".png"};

                for (String ext : extensions) {
                    File imageFile = new File("data/image/" + imageName + ext);
                    if (!imageFile.exists()) continue;

                    BufferedImage baseImage = ImageIO.read(imageFile);
                    if (baseImage != null && ImageUtils.imagesAreEqual(userImage, baseImage)) {
                        User.clearInstance();
                        User.init(
                            fields[2], fields[3], fields[0], fields[4],
                            fields[6], fields[5], Double.parseDouble(fields[9]),
                            fields[1], imageName, Boolean.parseBoolean(fields[7]),  
                            Boolean.parseBoolean(fields[6])
                        );
                        return true;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
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
                String[] fields = line.split(",", 10);
                if (fields.length == 10) {
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
    // Funcion principalmente usada para tests
    public boolean validateFullUser(User inputUser) {
    try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",", -1);
            if (fields.length == 10) {
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
                    fields[1],   // userType
                    fields[9],
                    Boolean.parseBoolean(fields[7]),
                    Boolean.parseBoolean(fields[8])
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
                    Objects.equals(inputUser.getImage(), storedUser.getImage()) &&
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
    public List<UserData> readAllUsers() {
        List<UserData> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(systemUserDataBAse))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";",11);
                if (fields.length == 11) {
                    String ID = fields[0];
                    String name = fields[2];
                    String lastname = fields[3];
                    String userName = fields[5];
                    boolean isAdmin = Boolean.parseBoolean(fields[7]);
                    users.add(new UserData(name, lastname, userName, isAdmin, ID));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void modifyPrivilege(String ID, boolean makeAdmin) {
        try {
            List<String> lines = Files.readAllLines(systemUserDataBAse.toPath());
            
            for (int i = 0; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(",", -1);
                if (fields.length >= 11 && fields[0].trim().equals(ID.trim())) {
                    fields[7] = String.valueOf(makeAdmin);
                    lines.set(i, String.join(",", fields));
                    break;
                }
            }
            
            Files.write(systemUserDataBAse.toPath(), lines);
        } catch (IOException e) {
            System.out.println("Error modifying privileges: " + e.getMessage());
        }
    }

    public void downPrivileg(String ID) {
        modifyPrivilege(ID, false);
    }

    public void upPrivileg(String ID) {
        modifyPrivilege(ID, true);
    }
}
