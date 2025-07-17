package controller;

import view.SignInView;
import model.User;
import model.persistence.UserFile;

public class SignInController {
    private SignInView view;
    public static final int INVALID_USER = 0, SUCCESS = 1, INVALID_FIELD_USER=2, INVALID_FIELD_PASSWORD=3;

    public SignInController(SignInView view){
        this.view=view;
        view.getSignInButton().addActionListener(e -> {
            int result = signIn();
            switch (result) {
                case 0:
                    view.setMessageAlert("Usuario Invalido");
                    break;
                case 1: 
                    view.setMessageAlert("Bienvenido");
                    break;
                case 2:
                    view.setMessageAlert("Por favor introduzca su usuario");
                    break;
                case 3:
                    view.setMessageAlert("Por favor introduzca su contrasenia");
                default:
                    view.setMessageAlert(null);;
                    break;
            }
        });
    }
    public int signIn(){
        String userName = view.getUserName().getText().trim();
        String userPassword = view.getPasswordField().getText().trim();
        
        UserFile userFile = new UserFile();

        if (userName.isEmpty() || userName.equals("Nombre de usuario")) { return INVALID_FIELD_USER; }
        if (userPassword.isEmpty()) { return INVALID_FIELD_PASSWORD; }

        if(userFile.userExists(userName, userPassword)){
            User user = User.getInstance(userName, userPassword, "id");
            return SUCCESS;
        }else{
            return INVALID_USER;
        }
    }
}
