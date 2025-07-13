package view;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JFrame {

    private RoundedTextField userNameField;
    private RoundedPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageAlert;

    public SignInView() {
        setTitle("Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa

        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel bgPanel = new BackgroundPanel(bgImage);
        bgPanel.setLayout(new GridBagLayout());

        // Panel central redondeado y translúcido
        RoundedPanel centerPanel = new RoundedPanel(40);
        centerPanel.setPreferredSize(new Dimension(350, 480));
        centerPanel.setBackground(new Color(255, 255, 255, 180));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Logo
        JLabel logo = new JLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        Image logoImg = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage();
        logoImg = logoImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoImg));

        // Campos
        userNameField = new RoundedTextField("Nombre de usuario");
        passwordField = new RoundedPasswordField("Contraseña");

        // Botón
        loginButton = new JButton("Acceder");
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);

        /* Mensajes de alerta */
        messageAlert = new JLabel("");
        messageAlert.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageAlert.setForeground(Color.RED);

        // Registrarse
        JLabel registrarse = new JLabel("Registrarse");
        registrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrarse.setForeground(Color.DARK_GRAY);

        // Agregar componentes
        centerPanel.add(logo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(userNameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(registrarse);
        centerPanel.add(messageAlert);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bgPanel.add(centerPanel, new GridBagConstraints());
        setContentPane(bgPanel);
        setVisible(true);
    }

    // Panel de fondo con imagen
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Panel redondeado
    class RoundedPanel extends JPanel {
        private final int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Campo de texto redondeado
    class RoundedTextField extends JTextField {
        private final int radius = 20;

        public RoundedTextField(String text) {
            super(text);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    // Campo de contraseña redondeado
    class RoundedPasswordField extends JPasswordField {
        private final int radius = 20;

        public RoundedPasswordField(String text) {
            super(text);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    /*Getters */
    public JTextField getUserName(){ return userNameField;}
    public JPasswordField  getPasswordField(){ return passwordField;}
    public JButton getSignInButton(){return loginButton;}
    /*Setters */
    public void setMessageAlert(String alert){
        messageAlert.setText(alert);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignInView::new);
    }
}