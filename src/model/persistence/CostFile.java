package model.persistence;

import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Cost;


public class CostFile {
    //Atrib
    private File costFile = new File("data/CostFile.txt");                         //Abrimos la base de datos

    // Test constructor
    public CostFile(File customFile) {
        this.costFile = customFile;
    }
    //Constructor del la base de dato de costo.
    public CostFile() {
        try {                                                                               //Si no abre el archivo rntonces
            if (!costFile.exists()) {                                                       //procedemos a cear el archivo
                costFile.createNewFile();
            }
        } catch (IOException e) {   
            System.out.println("Error creando el archivo de Secretaría: " + e.getMessage());
        }
    }
    //Funcion para guardar todos los datos de los costos.
    public void saveAll(List<Cost> costList){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(costFile))){                 //CReamos un bufferedWrite + FileWriter
            for(Cost cost : costList){                                                             //Por cada Elemento de la lista costo vamos a iterar
                String line = cost.getCategory() +","+              //Añadimos el costo con su Categoria
                            cost.getType() +","+                    //Tipo
                            cost.getName() +","+                    //Nombre
                            cost.getCost().toString();              //Valor
                writer.write(line);                                 //Escribimos en el archivo
                writer.newLine();
            }
        } catch (IOException e) {
        System.out.println("Error al guardar los costos: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
        JOptionPane.showMessageDialog(null, 
            e.getMessage(), 
            "Error en costos", 
            JOptionPane.ERROR_MESSAGE);
        }
    }
    //Con esta funcion se leet todos los datos de la base de datos.
    public List<Cost> readData(){
        List<Cost> costs = new ArrayList<>();                                           //CReamos una lista que retornaremos al finalizar
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){      
            String line;
            while ((line = reader.readLine()) != null) {                                //Mientras que podamos leer obtenemos los datos
                String fields[] = line.split(",", 4);                       //Sabemos que siempre seran 4 campos por lo que nuestro arreglo
                String category = fields[0];                                            //Tendra cuatro posiciones, categoria
                String type = fields[1];                                                //tipo
                String name = fields[2];                                                //nombre
                String value = fields[3];                                               //valor

                Cost cost = new Cost(type, category, name, Double.parseDouble(value));  //Creamos un nuevo costo(modelo)

                costs.add(cost);                                                        //Añadimos el costo a nuestra lista.    
            }
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return costs;                                                                   //Retornamos nuestra lista
    }
    //Funcion para sumar y retornar todos los costos fijos
    public double getAllFixedCost(){
        double result=0;                        
        try(BufferedReader reader = new BufferedReader(new FileReader(costFile))){
            String line;                                                                //Creamos un string que contendra toda la linea
            String hit = "Fijo";                                                        //Creamos un string con la palabra Hit
            while ((line = reader.readLine()) != null) {                                //Mientras que linea sea diferente a null leemos
                String fields[] = line.split(",", 4);                       //creamos un array que contendra los 4 diferentes campos
                  if(fields[0].equals(hit)) result += Double.parseDouble(fields[3]);    //Si la primera palabra del arreglo es "Fijo" entonces    
            }                                                                           //Obtenemos la ultima posicion del arreglo la cual es el valor y lo sumamos.
            return result;
        }catch(IOException e){
            System.out.println("Error al guardar los costos: " + e.getMessage());
        }
        return result;
    }
    //Funcion para sumar y retornar todos los costos variables
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
    //Funcion para guardar el CCB
    public void saveCCB(String fechas){
        
    }
    //Funcion para obtener el CCB guardado.
}
