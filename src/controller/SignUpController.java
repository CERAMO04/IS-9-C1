package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.SignUpView;
import model.User;
import model.persistence.UserFile;

public class SignUpController {
    private SignUpView view;

    public SignUpController(SignUpView view){
        this.view = view;

        this.view.getRegisterButton().addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpUser();
            }
        });
    }
    public void signUpUser(){
        UserFile systemDataFile = new UserFile();
        
        String name = view.getNameField().getText();
        String lastName = view.getLastNameField().getText();
        String ID = view.getIDField().getText();
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String email = view.getEmailField().getText();

        User curretUser = new User(name,lastName,ID,username,password,email);

        systemDataFile.checkAndSaveUser(curretUser);

    }
}   
