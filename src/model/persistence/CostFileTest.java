package model.persistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import model.Cost;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class CostFileTest {
    @TempDir
    Path tempDir;

     @Test
    void testNegativeCostPrevention() throws Exception {
        // 1. Create test file
        File testFile = tempDir.resolve("testCosts.txt").toFile();
        CostFile costFile = new CostFile(testFile);
        
        // 2. Prepare test data (with negative value)
        List<Cost> costs = List.of(
            new Cost("Fijo", "Cocina", "Gas", 120.0),
            new Cost("Variable", "Limpieza", "Desinfectante", -30.0) // Invalid
        );
        
        // 3. Verify exception is thrown
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
        new Cost("Fijo", "Cocina", "Gas", Double.NaN) // Invalid
    );
    
    assertThrows(IllegalArgumentException.class, () -> costFile.saveAll(costs));
    }
}
