package main;

import javax.swing.JFrame;
import controller.CostController;
import controller.SignInController;
import controller.SignUpController;
import view.CostManagementView;
import view.MenuView;
import view.SignInView;
import view.SignUpView;
import view.WalletView;

public class SistemaGestionComedorUniversitario {
    public static void main(String[] args) {
        CostManagementView prueba = new CostManagementView();
        CostController controller = new CostController(prueba);
    }
}