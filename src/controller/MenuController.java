package controller;

import javax.swing.JOptionPane;

import model.User;
import view.MenuView;

public class MenuController {
    //Atrib
    private MenuView view;
    private MainController mainController;
    //Constructor e inicializador de menuView, recibe una vista de menu y el control principal.
    public MenuController(MenuView menuView, MainController mainController){
        this.view = menuView;
        this.mainController = mainController;
        view.getLogOutButton().addActionListener(e->{              //Escuchamos el boton "Cerrar Sesion"
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Nos vemos pronto!");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });
        if (view.getCostButton() != null) {                       //Se pone un condicional verificando que sea diferente de null
            view.getCostButton().addActionListener(e -> {         //Ya que si lo colocamos de forma normal cuando un
                mainController.exitFrame(view);                   //Usuario "No Adm" intente logear entonces este boton   
                mainController.showCostManagementView();          //No existira y por tanto dara error
            });
        }
    }
}
