package view;

import javax.swing.*;
import java.awt.*;

public class SignUpView extends JFrame {
    /*^Componentes */
    private JTextField idField, userNameField;
    private JPasswordField  passwordField; 
    private JButton signUpButton;
    /*Probando registro de usuario */
    public SignUpView(){
        
        setTitle("Registro de Usuario");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*Panel de formulario */
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Username:"));
        userNameField = new JTextField();
        panel.add(userNameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        signUpButton = new JButton("Register");
        panel.add(signUpButton);

        add(panel);
        setVisible(true); 
    }
    /*Getters */
    public JTextField getIDField() { return idField; }
    public JTextField getUsernameField() { return userNameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JButton getRegisterButton() { return signUpButton; }
    /*Functions */
}