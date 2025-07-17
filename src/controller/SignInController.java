package controller;

import view.SignInView;
import model.User;
import model.persistence.UserFile;

public class SignInController {
    private SignInView view;
    private MainController mainController;

    public static final int INVALID_USER = 0, SUCCESS = 1, INVALID_FIELD_USER=2, INVALID_FIELD_PASSWORD=3;

    public SignInController(SignInView view, MainController mainController){
        this.view=view;
        this.mainController=mainController;

        view.getSignInButton().addActionListener(e -> {
            int result = signIn();
            switch (result) {
                case INVALID_USER:
                    view.setMessageAlert("Usuario Invalido");
                    break;
                case SUCCESS: 
                    view.setMessageAlert("Bienvenido");
                    mainController.exitFrame(view);
                    mainController.showMenu();
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
        view.getSignUpButton().addActionListener(e -> {
            mainController.exitFrame(view);
            mainController.ShowSignUp();
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
            user.createWallet();
            return SUCCESS;
        }else{
            return INVALID_USER;
        }
    }
}
