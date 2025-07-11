package main;

import controller.SignUpController;
import view.SignUpView;

public class SistemaGestionComedorUniversitario {

    public static void main(String[] args) {
        System.out.println("Hola mundo");
        SignUpView prueba = new SignUpView();
        SignUpController controller = new SignUpController(prueba);
    }
}