package controller;

import model.CCB;
import model.User;
import view.*;

public class MainController {
    private final WalletView walletView;
    WalletController walletController;
    //Constructor e incializador del programa completo. 
    public MainController(){
        User.init();
        CCB.loadFromFile();
        walletView = new WalletView();
        walletController =new WalletController(walletView);
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
        walletController.updateView();
        MenuView menuView = new MenuView(walletView);
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
        CostManagementView costView = new CostManagementView(walletView);
        new CostController(costView,this);
        costView.setVisible(true);
    }
    public void showRecarge(){
        RechargeView recargeView = new RechargeView(walletView);
        new RechargeController(recargeView,this,walletController);
        recargeView.setVisible(true);
    }
    public void showComparerView() {
        ComparerFaceView view = new ComparerFaceView();
        new ComparerFaceController(view, this);
        view.setVisible(true);
    }
    public void showUserManagement(){
        UserManagementView view = new UserManagementView();
        new UserManagementController(view,this);
        view.setVisible(true);
    }
    //Funcion para cerrar la ventana que se le pase por parametro. 
    public void exitFrame(javax.swing.JFrame view){
        view.dispose();
    }
}
