package controller;

import javax.swing.JOptionPane;
import model.User;
import view.MenuView;
import model.persistence.MenuFile;

public class MenuController {
    private MenuView view;
    private MainController mainController;

    public MenuController(MenuView menuView, MainController mainController){
        this.view = menuView;
        this.mainController = mainController;

        MenuFile dataFile = new MenuFile();
        String[] menuData = dataFile.readMenu(); 

        if (menuData[0] != null) {
            view.getBreakfastPanel().setDescription(menuData[0]);
        }
        if (menuData[1] != null) {
            view.getLunchPanel().setDescription(menuData[1]);
        }

        view.getLogOutButton().addActionListener(e -> {
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Nos vemos pronto!");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });

        view.getRechargeButton().addActionListener(e -> {
            mainController.exitFrame(menuView);
            mainController.showRecarge();
        });

        if (User.getInstance().getIsAdmin()){
            view.getEditButton().addActionListener(e -> {
                view.getBreakfastPanel().setEditMode(true);
            });
        }

        if (view.getCostButton() != null) {
            view.getCostButton().addActionListener(e -> {
                mainController.exitFrame(view);
                mainController.showCostManagementView();
            });
        }
    }
}
       
