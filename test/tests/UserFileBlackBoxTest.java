package tests;

import model.User;
import model.persistence.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UserFileBlackBoxTest {
    private static final String TEST_FILE = "data/SystemUsers_TEST.txt";
    private UserFile userFile;

    @BeforeEach
    void setup() throws Exception {
        // Se asegura de que el directorio existe
        new File("data").mkdirs();
        
        new File(TEST_FILE).delete();
        
        // Inicializar archivo de prueba
        userFile = new UserFile();
        userFile.setSystemUserDatabase(new File(TEST_FILE));
    }

    @AfterEach
    void cleanup() {
        // Borrar txt de prueba al final de cada test
        new File(TEST_FILE).delete();
    }

    // Test para verificar que el usuario es correctamente guardado en la base de datos del sistema
    @Test
    void testFullUserValidation() {
    // Guarda un usuario de prueba
    User.init("John", "Doe", "20000009", "jd@gmail.com", "passtest", "Johnny", 50.0, "comensal","image1",false,false);
    User testUser = User.getInstance();
    testUser.setIsAdmin(false);
    userFile.saveUser(testUser);

    // Creacion del usuario esperado
    User.clearInstance();
    User.init("John", "Doe", "20000009", "jd@gmail.com", "passtest", "Johnny", 50.0, "comensal","image1",false,false);
    User validationUser = User.getInstance();
    validationUser.setIsAdmin(false);

    assertTrue(userFile.validateFullUser(validationUser)); // User guardado es igual al de validacion
    }

    // Test para verificar que un usuario no existente en SystemUsers.txt no pueda loguearse
    @Test
    void testUserDoesNotExist() {
    assertFalse(userFile.userExists("999")); // ID no existente
    assertFalse(userFile.userExists("ghost", "123")); // Credenciales falsas
    }

    public static void main(String[] args) {

    UserFileBlackBoxTest test = new UserFileBlackBoxTest();
    
    try {
        // Set up
        test.setup();
        
        // Correr pruebas
        test.testFullUserValidation();
        test.testUserDoesNotExist();
        
        System.out.println("Todas las pruebas pasaron");
    } catch (Exception e) {
        System.err.println("Prueba fallida: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Clean up
        test.cleanup();
    }
}
}