package main;

import controller.MainController;

public class SistemaGestionComedorUniversitario {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        /*Se crea la clase MainController que controla toda la aplicacion en ejecucion*/
        new MainController();                    
    }
}