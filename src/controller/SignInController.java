package controller;

import javax.swing.JOptionPane;
import view.SignInView;
import model.persistence.UserFile;


public class SignInController {
    private SignInView view;
    public static final int USUARIO_INVALIDO = 0;
    public static final int SUCCESS = 1;

    public SignInController(SignInView view){
        this.view=view;
        view.getSignInButton().addActionListener(e -> {
            int result = signIn();
            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(view, "Usuario Invalido");
                    break;
                case 1: 
                    JOptionPane.showMessageDialog(view, "Bienvenido");
                    break;
                default:
                    break;
            }
        });
    }
    public int signIn(){
        String username = view.getUserName().getText().trim();
        String password = view.getPasswordField().getText().trim();
        
        UserFile userFile = new UserFile();

        if(userFile.userExists(username, password)){
            return SUCCESS;
        }else{
            return USUARIO_INVALIDO;
        }
    }
}
