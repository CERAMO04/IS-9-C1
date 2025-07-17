package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import view.SignUpView;
import model.User;
import model.persistence.UserFile;
import model.persistence.SecretaryFile;

public class SignUpController {
    
    private SignUpView view;
    private MainController mainController;

    public static final int ALREADY_REGISTERED = 0, SUCCESS = 1, NOT_IN_SECRETARY = 2;

    public SignUpController(SignUpView view, MainController mainController){
        this.view = view;
        this.mainController = mainController;
        
        view.getRegisterButton().addActionListener(e -> {
            int result = signUpUser();
            switch(result) {
            case SUCCESS:
                JOptionPane.showMessageDialog(view, "Registro exitoso");
                mainController.exitFrame(view);
                mainController.showLogIn();
                break;
            case ALREADY_REGISTERED:
                view.setMessageAlert("El usuario ya esta registrado en el sistema del comedor");
                break;
            case NOT_IN_SECRETARY:
                view.setMessageAlert("El usuario no se encuentra en la base de dato de Secretaria UCV");
                break;
        }
        });
        view.getBackToLoginLink().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.exitFrame(view);
                mainController.showLogIn();
            }
        });
    }
    public int signUpUser(){        
        String ID = view.getIDField().getText().trim();
        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText().trim();

        User currentUser = new User(username, password, ID);

        SecretaryFile secretaryDataBase = new SecretaryFile();
        UserFile userFile = new UserFile();

        if (userFile.userExists(ID)) { return ALREADY_REGISTERED; } 

        if (secretaryDataBase.readSecretaryDataBase(currentUser)) {
            userFile.saveUser(currentUser);
            return SUCCESS;
        } else {
            return NOT_IN_SECRETARY;
        }
    }
}

