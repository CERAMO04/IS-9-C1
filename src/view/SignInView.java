package view;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JFrame{
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton signInButton;

    public SignInView(){
        setTitle("Iniciar Sesion");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,1,10,10));

        panel.add(new JLabel("Usuario"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Usuario"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        signInButton = new JButton("Register");
        panel.add(signInButton);

        add(panel);
        setVisible(true);
    }
    /*Getters */
    public JTextField getUserField(){ return userField; }
    public JPasswordField getPasswordField(){ return passwordField; }    
    public JButton getSingInButton(){ return signInButton; }

}
