package controller;

import javax.swing.JOptionPane;

import model.User;
import view.MenuView;

public class MenuController {
    private MenuView view;
    private MainController mainController;

    public MenuController(MenuView menuView, MainController mainController){
        this.view = menuView;
        this.mainController = mainController;

        view.getLogOutButton().addActionListener(e->{
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Cerrando sesion");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });
        if (view.getCostButton() != null) {
            view.getCostButton().addActionListener(e -> {
                mainController.exitFrame(view);
                mainController.showCostManagementView();
            });
        }
    }
}
