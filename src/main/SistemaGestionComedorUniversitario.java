package main;

import controller.CostController;
import controller.SignInController;
import controller.SignUpController;
import view.CostManagementView;
import view.SignInView;
import view.SignUpView;

public class SistemaGestionComedorUniversitario {
    public static void main(String[] args) {
        CostManagementView prueba = new CostManagementView();
        CostController controller = new CostController(prueba);
    }
}