package controller;


import javax.swing.JOptionPane;

import model.User;
import view.MenuView;




public class MenuController {
    private MenuView menuView;
    private MainController mainController;

    public MenuController(MenuView menuView, MainController mainControllet){
        this.view = menuView;
        this.mainController = mainController;

        view.getLogOutButton().addActionListener(e->{
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Cerrando sesion");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });
        view.getCostManagementButton().addActionListener(e->{
            mainController.exitFrame(view);
            maincontroller.showCostManagementView();
        });
    }


}
