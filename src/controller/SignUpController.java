package controller;

import javax.swing.JOptionPane;
import view.SignUpView;
import model.User;
import model.persistence.UserFile;
import model.persistence.SecretaryFile;

public class SignUpController {
    
    private SignUpView view;
    public static final int ALREADY_REGISTERED = 0;
    public static final int SUCCESS = 1;
    public static final int NOT_IN_SECRETARY = 2;

    public SignUpController(SignUpView view){
        this.view = view;
        this.view.getRegisterButton().addActionListener(e -> {
            int result = signUpUser();
            switch(result) {
                case 1:
                    JOptionPane.showMessageDialog(view, "Registro exitoso.");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(view, "El usuario ya está registrado en el sistema del comedor.");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(view, "El usuario no está en la base de datos de la secretaría.");
                    break;
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

