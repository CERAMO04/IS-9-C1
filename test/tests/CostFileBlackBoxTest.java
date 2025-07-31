package tests;

import model.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import model.Cost;


public class CostFileBlackBoxTest {
    @TempDir
    Path tempDir;

    @Test
    void testNegativeCostPrevention() throws Exception {
        // Crea el archivo de prueba
        File testFile = tempDir.resolve("testCosts.txt").toFile();
        CostFile costFile = new CostFile(testFile);
        
        // Preparar los datos
        List<Cost> costs = List.of(
            new Cost("Fijo", "Cocina", "Gas", 120.0),
            new Cost("Variable", "Limpieza", "Desinfectante", -30.0) // Invalido
        );
        
        // Verificar exception 
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> costFile.saveAll(costs)
        );
        
        assertTrue(ex.getMessage().contains("no puede ser negativo"));
    }

    @Test
    void testNaNValueRejection() {
    File testFile = new File("data/CostFile_TEST.txt");
    CostFile costFile = new CostFile(testFile);
    
    List<Cost> costs = List.of(
        new Cost("Fijo", "Cocina", "Gas", Double.NaN) // Invalido
    );
    
    assertThrows(IllegalArgumentException.class, () -> costFile.saveAll(costs));
    }
}
