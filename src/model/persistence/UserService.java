package model.persistence;

import model.User;
import model.UserData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private static final String USER_FILE = "data/SystemUsers.txt";

    private static UserData toUserData(User user) {
        return new UserData(
            user.getName(),
            user.getLastName(),
            user.getUser(),
            user.getIsAdmin(),
            user.getID()
        );
    }

    public static List<UserData> getAllUsers() throws IOException {
        List<UserData> users = new ArrayList<>();
        List<String> lines = Files.readAllLines(new File(USER_FILE).toPath());
        
        String currentUserId = null;
        try {
            currentUserId = User.getInstance().getID();
        } catch (IllegalStateException e) {
            // Robustez, no usuario logueado
        }

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 11) {
                String userId = parts[0];
                boolean isSuperSu = Boolean.parseBoolean(parts[8]);
                
                if (isSuperSu || (currentUserId != null && currentUserId.equals(userId))) {
                    continue;
                }
                
                users.add(new UserData(
                    parts[2],  // name
                    parts[3],  // lastName
                    parts[5],  // userName
                    Boolean.parseBoolean(parts[7]),  // isAdmin
                    parts[0]   // ID
                ));
            }
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
            original.getImage(),
            original.getIsSuperSu(),
            original.getIsAdmin()
        );
        User clone = User.getInstance();
        User.clearInstance();
        return clone;
    }

    public static void promoteToAdmin(String userId) throws IOException {
        modifyUserPrivilege(userId, true);
    }

    public static void revokeAdmin(String userId) throws IOException {
        modifyUserPrivilege(userId, false);
    }

    public static void modifyUserPrivilege(String userId, boolean makeAdmin) throws IOException {
        List<String> lines = Files.readAllLines(new File(USER_FILE).toPath());
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 11 && parts[0].equals(userId)) {  
                parts[7] = String.valueOf(makeAdmin); 
                lines.set(i, String.join(",", parts));
                break;
            }
        }
        
        Files.write(new File(USER_FILE).toPath(), lines);
    }

    public static boolean userExists(String userId) throws IOException {
        return getAllUsers().stream()
               .anyMatch(user -> user.getID().equals(userId));
    }
}