package model.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import model.CCB;

public class CCBFile {
    private File CCBFile = new File("data/CCBFile.txt");   

    public CCBFile() {
        try {                                                                              //Si no abre el archivo rntonces
            if (!CCBFile.exists()) {                                                       //procedemos a cear el archivo
                CCBFile.createNewFile();
            }
        } catch (IOException e) {   
            System.out.println("Error creando el archivo de Secretaría: " + e.getMessage());
        }
    }
    public void saveCCB(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CCBFile))){         
            writer.write(CCB.getInstance().getStartDate());
            writer.newLine();
            writer.write(CCB.getInstance().getEndDate());
            writer.newLine();
            writer.write(String.valueOf(CCB.getInstance().getValueCCB()));
        } catch (IOException e) {
        System.out.println("Error al guardar el CCB: " + e.getMessage());
        }
    }
    public void readCCB() {
        if (!CCBFile.exists() || CCBFile.length() == 0) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(CCBFile))) {
            String startDate = reader.readLine(); 
            String endDate = reader.readLine();    
            String valueLine = reader.readLine(); 

            if (startDate != null && endDate != null && valueLine != null) {
                double value = Double.parseDouble(valueLine);
                CCB.init(value, startDate, endDate);
            } else {
                System.out.println("Archivo CCB incompleto o mal formateado.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el CCB: " + e.getMessage());
        }
    }
}   
