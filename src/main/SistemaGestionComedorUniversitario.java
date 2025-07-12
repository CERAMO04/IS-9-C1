package main;

import controller.SignInController;
import controller.SignUpController;
import view.SignInView;
import view.SignUpView;

public class SistemaGestionComedorUniversitario {

    public static void main(String[] args) {
        SignInView prueba = new SignInView();
        SignInController controller = new SignInController(prueba);
    }
}