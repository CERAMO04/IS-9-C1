package model.persistence;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UserFileTest {
    private UserFile userFile;

    @BeforeEach
    void setup() throws Exception {
    
        userFile = new UserFile();
    }

    @Test
    void testSaveAndCheckUser() {
    // Setup
    User.init("John", "Doe", "20000009", "jd@gmail.com", "passtest", "Johnny", 0.0, "comensal");
    
    // Test
    userFile.saveUser(User.getInstance());
    
    // Verificar
    assertTrue(userFile.userExists("20000009")); // Chequear ID
    assertTrue(userFile.userExists("Johnny", "passtest")); // Chequear credenciales
    }

    @Test
    void testUserDoesNotExist() {
    assertFalse(userFile.userExists("999")); // ID no existente
    assertFalse(userFile.userExists("ghost", "123")); // Credenciales falsas
    }

    public static void main(String[] args) {

    UserFileTest test = new UserFileTest();
    
    try {
        // Set up
        test.setup();
        
        // Run tests
        test.testSaveAndCheckUser();
        test.testUserDoesNotExist();
        
        System.out.println("All tests passed!");
    } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
    }
}
}