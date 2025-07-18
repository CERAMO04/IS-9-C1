package controller;

import view.*;
import controller.*;

public class MainController {
        
    public MainController(){
        showLogIn();
    }
    public void showLogIn(){
        SignInView signInView = new SignInView();
        new SignInController(signInView,this);
        signInView.setVisible(true);
    }
    public void showMenu(){
        MenuView menuView = new MenuView();
        menuView.setVisible(true);
    }
    public void ShowSignUp(){
        SignUpView SignUpView = new SignUpView();
        new SignUpController(SignUpView,this);
        SignUpView.setVisible(true);
    }
    public void CostManagementView(){
        CostManagementView costView = new CostManagementView();
        new CostController(costView,this);
    }
    public void exitFrame(javax.swing.JFrame view){
        view.dispose();
    }
}
