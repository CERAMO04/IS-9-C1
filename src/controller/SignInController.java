package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.SignInView;
import model.persistence.UserFile;

public class SignInController {
    //Atrib 
    private SignInView view;
    private MainController mainController;
    //Valores genericos para informe de errores.
    public static final int INVALID_USER = 0, SUCCESS = 1, INVALID_FIELD_USER=2, INVALID_FIELD_PASSWORD=3;
    //Iniciamos la view de login, recibimos una vista del login de usuario y un main controller   
    public SignInController(SignInView view, MainController mainController){
        this.view=view;
        this.mainController=mainController;

        view.getSignInButton().addActionListener(e -> {                             //Escuchamos boton de login
            int result = signIn();                                                  //Invocamos funcion para investigar
            switch (result) {                                                       //Si el usuario esta en la base de datos 
                case INVALID_USER:                                                  //Del comedor una vez recibido el resultado 
                    view.setMessageAlert("Usuario Inválido");                 //El switch nos otorga un mensaje correcto
                    break;                                                           
                case SUCCESS: 
                    view.setMessageAlert("Bienvenido");                       //Al estar todo "OK" se cierra el login view
                    mainController.exitFrame(view);                                 //Y se muestra la vista del Menu
                    mainController.showMenu();
                    break;
                case INVALID_FIELD_USER:
                    view.setMessageAlert("Por favor introduzca su alias");
                    break;
                case INVALID_FIELD_PASSWORD:
                    view.setMessageAlert("Por favor introduzca su contraseña");
                    break;
                default:
                    view.setMessageAlert(null);;
                    break;
            }
        });
        view.getSingUpLabel().addMouseListener(new MouseAdapter(){                  //Escuchamos letras de "Registrar"
            @Override                                                               //Cerramos login y mostramos
            public void mouseClicked(MouseEvent e) {                                //Registrar.
                mainController.exitFrame(view);
                mainController.ShowSignUp();
            }
        });
        view.getScanButton().addActionListener(e ->{
            mainController.exitFrame(view);
            mainController.showComparerView();
        });
    }
    
    //Funcion que revisa si el usuario existe.
    public int signIn(){
        String userName = view.getUserName().getText().trim();                       //Recibimos el User y la contraseña de los    
        String userPassword = view.getPasswordField().getText().trim();              //TextField de login. 
        
        if (userName.isEmpty()){    //Si esta vacio el campo o no se ha modificado
            return INVALID_FIELD_USER; }
        if (userPassword.isEmpty()){ 
            return INVALID_FIELD_PASSWORD; }                                         //Retornamos un mensaje de error

        UserFile userFile = new UserFile();                                          //Abrimos la base de datos del comedor.

        if(userFile.userExists(userName, userPassword)){                             //Invocamos a la funcion que se encarga
            return SUCCESS;                                                          //De leer la base de datos del comedor
        }else{                                                                       //Y esperamos una respuesta.
            return INVALID_USER;                                                     //Respondemos con el mensaje correspondiente
        }
    }
}
