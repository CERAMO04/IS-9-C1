package controller;

import view.*;
import controller.*;

public class MainController {
    //Constructor e incializador del programa completo. 
    public MainController(){
        showLogIn();
    }
    //Funcion para abrir el LogIn Con su controlador
    public void showLogIn(){
        SignInView signInView = new SignInView();
        new SignInController(signInView,this);
        signInView.setVisible(true);
    }
    //Funcion para abrir el Menu Con su controlador
    public void showMenu(){
        MenuView menuView = new MenuView();
        new MenuController(menuView,this);
        menuView.setVisible(true);
    }
    //Funcion para abrir el Registrar Con su controlador    
    public void ShowSignUp(){
        SignUpView SignUpView = new SignUpView();
        new SignUpController(SignUpView,this);
        SignUpView.setVisible(true);
    }
    //Funcion para abrir la Gestion de costos Con su controlador  
    public void showCostManagementView(){
        CostManagementView costView = new CostManagementView();
        new CostController(costView,this);
        costView.setVisible(true);
    }
    //Funcion para cerrar la ventana que se le pase por parametro. 
    public void exitFrame(javax.swing.JFrame view){
        view.dispose();
    }
}
