package model.persistence;

import model.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String USER_FILE = "data/SystemUsers.txt";

    public static List<User> getAllUsers() throws IOException {
    List<User> users = new ArrayList<>();
        List<String> lines = Files.readAllLines(new File(USER_FILE).toPath());
        
        // Obtiene ID si existe
        String currentUserId = null;
        try {
            currentUserId = User.getInstance().getID();
        } catch (IllegalStateException e) {
            // No hay usuario logueado (robustez)
        }
        
        // Guarda la instancia original si existe
        User originalInstance = null;
        try {
            originalInstance = User.getInstance();
        } catch (IllegalStateException e) {
            // Robustez en caso de no estar logueado
        }
        
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                // Salta el usuario si es el que está logueado
                if (currentUserId != null && currentUserId.equals(parts[0])) {
                    continue;
                }
                
                // Limpia la instancia antes dde crear otra
                User.clearInstance();
                
                // Crear instancia
                User.init(
                    parts[2], // name
                    parts[3], // lastName
                    parts[0], // ID
                    parts[4], // email
                    parts[5], // password
                    parts[6], // username
                    Double.parseDouble(parts[8]), // value
                    parts[1], // userType
                    parts[9]  // image
                );
                User user = User.getInstance();
                user.setIsAdmin(Boolean.parseBoolean(parts[7]));
                users.add(cloneUser(user));
            }
        }
        
        // Restaura instancia original si existía
        if (originalInstance != null) {
            User.init(
                originalInstance.getName(),
                originalInstance.getLastName(),
                originalInstance.getID(),
                originalInstance.getEmail(),
                originalInstance.getPassword(),
                originalInstance.getUser(),
                originalInstance.getWallet().getBalance(),
                originalInstance.getUserType(),
                originalInstance.getImage()
            );
            User.getInstance().setIsAdmin(originalInstance.getIsAdmin());
        }
        
        return users;
    }

    private static User cloneUser(User original) {
        User.init(
            original.getName(),
            original.getLastName(),
            original.getID(),
            original.getEmail(),
            original.getPassword(),
            original.getUser(),
            original.getWallet().getBalance(),
            original.getUserType(),
            original.getImage()
        );
        User clone = User.getInstance();
        clone.setIsAdmin(original.getIsAdmin());
        User.clearInstance(); // Limpia luego de clonar
        return clone;
    }

    public static void promoteToAdmin(String userId) throws IOException {
        List<String> lines = Files.readAllLines(new File(USER_FILE).toPath());
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(userId)) {
                parts[7] = "true"; // Promueve a admin
                lines.set(i, String.join(",", parts));
                break;
            }
        }
        
        Files.write(new File(USER_FILE).toPath(), lines);
    }

    public static void revokeAdmin(String userId) throws IOException {
    List<String> lines = Files.readAllLines(new File(USER_FILE).toPath());
    
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(userId)) {
                parts[7] = "false"; // Revoca permisos
                lines.set(i, String.join(",", parts));
                break;
            }
        }
        
        Files.write(new File(USER_FILE).toPath(), lines);
    }
}