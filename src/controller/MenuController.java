package controller;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import utils.TextUtils;
import view.MenuView;
import view.MenuView.MealCardPanel;
import view.UserManagementView;
import model.User;
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
                MealCardPanel breakfastPanel, lunchPanel;   
                breakfastPanel = view.getBreakfastPanel();
                lunchPanel = view.getLunchPanel();
                view.setEditModeOn(breakfastPanel, lunchPanel);
            });
        }

        if (User.getInstance().getIsSuperSu()){
            view.getAdminManagementButton().addActionListener(e ->{
                mainController.showUserManagement();
            });
        }
        

        if (view.getSaveButton() != null){
            view.getSaveButton().addActionListener(e ->{
                MealCardPanel breakfastPanel, lunchPanel;   
                breakfastPanel = view.getBreakfastPanel();
                lunchPanel = view.getLunchPanel();

                String breakfast = breakfastPanel.getEditedText();
                String lunchText = lunchPanel.getEditedText();

                if (!TextUtils.isValidText(breakfast) || !TextUtils.isValidText(lunchText)) {
                    JOptionPane.showMessageDialog(view,
                        "Solo se permiten letras, espacios y puntuación básica.\nEvita números o símbolos especiales.",
                        "Entrada inválida", JOptionPane.WARNING_MESSAGE);
                    return; // No guardar si no pasa la validación
                }
                dataFile.SaveDailyMenu(breakfast, lunchText);

                view.setEditmodeOff(breakfastPanel, lunchPanel);
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
       
