package tests;

import model.User;
import model.persistence.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecretaryFileBlackBoxTest {
    private static final String TEST_FILE = "data/SecretaryUsers_TEST.txt";
    private SecretaryFile secretaryFile;

    @BeforeEach
    void setUp() throws IOException {
        // Setup del archivo de prueba
        Files.write(Paths.get(TEST_FILE), List.of(
            "27564321,comensal,Ana,Gonzalez,ana@gmail.com",
            "10000001,admin,Admin,Principal,admin@gmail.com"
        ));
    
        secretaryFile = new SecretaryFile() {
            @Override
            public File getSecretaryDatabaseFile() {
                return new File(TEST_FILE);
            }
        };
    }

    @AfterEach
    void clean() throws IOException {
        // Clean up
        Files.deleteIfExists(Paths.get(TEST_FILE));
        User.clearInstance();
    }

    @Test
    void testValidUser() {
        User.init("Ana", "Gonzalez", "27564321", "ana@gmail.com", "pw", "ana", 0.0, "comensal","image1");
        assertTrue(secretaryFile.readSecretaryDataBase(User.getInstance()));
        User.clearInstance();
    }

    @Test 
    void testInvalidUser() {
        User.init("Test", "User", "99999999", "test@test.com", "pw", "testuser", 0.0, "comensal","image1");
        assertFalse(secretaryFile.readSecretaryDataBase(User.getInstance()));
        User.clearInstance();
    }

    public static void main(String[] args) {
        SecretaryFileBlackBoxTest test = new SecretaryFileBlackBoxTest();
        
        try {
            // Set up
            test.setUp();
            
            // Run tests
            test.testValidUser();
            test.testInvalidUser();
            
            System.out.println("Todas las pruebas pasaron");
        } catch (Exception e) {
            System.err.println("Prueba fallida: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Clean up
                test.clean();
            } catch (IOException e) {
                System.err.println("Error durante la limpieza: " + e.getMessage());
            }
        }
    }
}