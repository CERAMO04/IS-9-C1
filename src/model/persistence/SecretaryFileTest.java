package model.persistence;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecretaryFileTest {
    private static final String TEST_FILE = "data/SecretaryUsers_TEST.txt";
    private SecretaryFile secretaryFile;

    @BeforeEach
    void setUp() throws IOException {
        // Setup test file
        Files.write(Paths.get(TEST_FILE), List.of(
            "27564321,comensal,Ana,Gonzalez,ana@gmail.com",
            "10000001,admin,Admin,Principal,admin@gmail.com"
        ));
    
        secretaryFile = new SecretaryFile() {
            @Override
            protected File getSecretaryDatabaseFile() {
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
        User.init("Ana", "Gonzalez", "27564321", "ana@gmail.com", "pw", "ana", 0.0, "comensal");
        assertTrue(secretaryFile.readSecretaryDataBase(User.getInstance()));
        User.clearInstance();
    }

    @Test 
    void testInvalidUser() {
        User.init("Test", "User", "99999999", "test@test.com", "pw", "testuser", 0.0, "comensal");
        assertFalse(secretaryFile.readSecretaryDataBase(User.getInstance()));
        User.clearInstance();
    }
}