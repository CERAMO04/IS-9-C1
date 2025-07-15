package model.persistence;

import model.Cost;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CostFile {
    
    private File costFile = new File("data/CostFile.txt");

    public CostFile() {
        try {
            if (!costFile.exists()) {
                costFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creando el archivo de Secretaría: " + e.getMessage());
        }
    }
    public void saveAll(List<Cost> costList){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(costFile))){
            for(Cost cost : costList){
                String line = cost.getCategory() +","+
                            cost.getType() +","+
                            cost.getName() +","+
                            cost.getCost().toString(); 
                writer.write(line);
                writer.newLine();
            }
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
    }
    public List<Cost> readData(){
        List<Cost> costs = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                String fields[] = line.split(",", 4);
                String category = fields[0];
                String type = fields[1];
                String name = fields[2];
                String value = fields[3];

                Cost cost = new Cost(type, category, name, Double.parseDouble(value));

                costs.add(cost);
            }

        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return costs;
    }
    public double getAllExtraCost(){
        double result=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){
            String line;
            String hit = "Extra";
            while ((line = reader.readLine()) != null) {
                String fields[] = line.split(",", 4);
                if(fields[0].equals(hit)) result += Double.parseDouble(fields[3]);
            }
            return result;
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return result;
    }

    public double getAllFixedCost(){
        double result=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){
            String line;
            String hit = "Fijo";
            while ((line = reader.readLine()) != null) {
                String fields[] = line.split(",", 4);
                  if(fields[0].equals(hit)) result += Double.parseDouble(fields[3]);
            }
            return result;
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return result;
    }
    public double getAllVariableCost(){
        double result=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){
            String line;
            String hit = "Variable";
            while ((line = reader.readLine()) != null) {
                String fields[] = line.split(",", 4);
                  if(fields[0].equals(hit)) result += Double.parseDouble(fields[3]);
            }
            return result;
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return result;
    }
}
