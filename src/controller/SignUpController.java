package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import view.SignUpView;
import model.User;
import model.persistence.UserFile;
import model.persistence.SecretaryFile;

public class SignUpController {
    //Atrib.
    private SignUpView view;
    private MainController mainController;
    //Valores genericos para los mensajes de errores.
    public static final int ALREADY_REGISTERED = 0, SUCCESS = 1, NOT_IN_SECRETARY = 2, EMPTY_FIELD_ID = 3, EMPTY_FIELD_USERNAME = 4, 
    EMPTY_FIELD_PASSWORD = 5, INVALID_FIELD_PASSWORD = 6;
    //Iniciamos el controlador que recibe una vista del "Registrar" y el maincontroller
    public SignUpController(SignUpView view, MainController mainController){
        this.view = view;
        this.mainController = mainController;
        
        view.getRegisterButton().addActionListener(e -> {                                   //Escuchamos el boton de "Registrar"
            int result = signUpUser();                                                      //Invocamos a la funcion que se encargara
            switch(result) {                                                                //De realizar las diversas verificaciones
            case SUCCESS:                                                                   //Esperamos un resultado y en base a ello
                JOptionPane.showMessageDialog(view, "Registro exitoso");            //Mostramos el mensaje correspondiente.
                mainController.exitFrame(view);                                             //Si el registro es exitoso, cerramos la vista "Registro"
                mainController.showLogIn();                                                 //Y regresamos al login     
                break;
            case ALREADY_REGISTERED:
                view.setMessageAlert("El usuario ya esta registrado en el sistema del comedor");
                break;
            case NOT_IN_SECRETARY:
                view.setMessageAlert("El usuario no se encuentra en la base de dato de Secretaria UCV");
                break;
            case EMPTY_FIELD_ID:
                view.setMessageAlert("Por favor ingresa tu cédula");
                break;
            case EMPTY_FIELD_USERNAME:
                view.setMessageAlert("Por favor ingresa un alias");
                break;
            case EMPTY_FIELD_PASSWORD:
                view.setMessageAlert("Por favor ingresa una contraseña");
                break;
            case INVALID_FIELD_PASSWORD:
                view.setMessageAlert("La contraseña no puede ser menor a 6 caracteres");
                break;    
        }
        });
        view.getBackToLoginLink().addMouseListener(new MouseAdapter() {                     //Escuchamos las letras de "Iniciar sesion"
            @Override
            public void mouseClicked(MouseEvent e) {                                        //Cerramos el "Register view" y pasamos al
                mainController.exitFrame(view);                                             //LogIn
                mainController.showLogIn();
            }
        });
    }
    //Funcion que se encarga de registrar al nuevo usuario.
    public int signUpUser(){        
        String ID = view.getIDField().getText().trim();                         //Recibimos el ID para verificarlo con la base
        String username = view.getUsernameField().getText().trim();             //De datos de secretaria + nombre + password
        String password = view.getPasswordField().getText().trim();

        if (ID.isEmpty() || ID.equals("Cédula de Identidad")) {
            return EMPTY_FIELD_ID;
        }
        if (username.isEmpty() || ID.equals("Alias")) {
            return EMPTY_FIELD_USERNAME;
        }
        if (password.isEmpty() || ID.equals("Contraseña")) {
            return EMPTY_FIELD_PASSWORD;
        }
        if (password.length() < 6) {
            return INVALID_FIELD_PASSWORD;
        }

        User.clearInstance();
        User.init(username, password, ID);                                      //Inicializamos un usuario con los 3 datos obtenidos
        User currentUser = User.getInstance();

        SecretaryFile secretaryDataBase = new SecretaryFile();                  //Abrimos tanto la base de dato de Secretaria
        UserFile userFile = new UserFile();                                     //Como la del comedor.

        if (userFile.userExists(ID)) { return ALREADY_REGISTERED; }             //Si el usuario ya esta registrado retornamos un mensaje

        if (secretaryDataBase.readSecretaryDataBase(currentUser)) {             //Si al leer la base se secretaria el ID del usuario coicide
            userFile.saveUser(currentUser);                                     //Con la base de datos de secretaria entonces
            return SUCCESS;                                                     //Procedemos a guardar el usuario en la base de datos del comedor
        } else {
            return NOT_IN_SECRETARY;
        }
    }
}