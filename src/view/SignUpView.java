package view; // Asegúrate de que el paquete sea el correcto para tu proyecto

import javax.swing.*;
import java.awt.*;

// Se mantiene la estructura de tu clase, pero con el diseño visual integrado.
public class SignUpView extends JFrame {

    // Se declaran como JTextField/JPasswordField estándar.
    private JTextField idField, userNameField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    
    // --- COMPONENTES ADICIONALES PARA LA NUEVA UI ---
    private JLabel backToLoginLink;
    private JLabel messageAlert;

    public SignUpView() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Usamos pantalla completa como en las otras vistas

        // --- ESTRUCTURA VISUAL MODERNA ---
        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel bgPanel = new BackgroundPanel(bgImage);
        bgPanel.setLayout(new GridBagLayout());

        RoundedPanel centerPanel = new RoundedPanel(40);
        centerPanel.setPreferredSize(new Dimension(380, 520));
        centerPanel.setBackground(new Color(255, 255, 255, 180));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel logo = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/assets/logo.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- INICIALIZACIÓN PERSONALIZADA ---
        // los inicializamos como nuestra versión con placeholder.
        idField = new PlaceholderTextField("Cédula de Identidad");
        userNameField = new PlaceholderTextField("Nombre de usuario");
        passwordField = new PlaceholderPasswordField("Contraseña");

        // Usamos el nombre de tu variable original para el botón
        signUpButton = new JButton("Registrarse");
        signUpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        messageAlert = new JLabel(" ");
        messageAlert.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageAlert.setForeground(Color.RED);

        backToLoginLink = new JLabel("¿Ya tienes cuenta? Inicia sesión");
        backToLoginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLoginLink.setForeground(Color.DARK_GRAY);
        backToLoginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Agregamos los componentes al panel central
        centerPanel.add(logo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(idField); // Usamos tus variables originales
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(userNameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(backToLoginLink);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(messageAlert);

        bgPanel.add(centerPanel, new GridBagConstraints());
        setContentPane(bgPanel);
        // setVisible(true); // Es mejor que el controlador haga visible la ventana
    }
    
    // --- TUS GETTERS ORIGINALES ---
    public JTextField getIDField() { return idField; }
    public JTextField getUsernameField() { return userNameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JButton getRegisterButton() { return signUpButton; }
    
    // Getters para los nuevos componentes (útiles para el controlador)
    public JLabel getBackToLoginLink() { return backToLoginLink; }
    public void setMessageAlert(String message, Color color) {
        messageAlert.setText(message);
        messageAlert.setForeground(color);
    }
    
    // --- CLASES INTERNAS PARA EL DISEÑO ---
    
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;
        public BackgroundPanel(Image image) { this.backgroundImage = image; }
        @Override
        protected void paintComponent(Graphics g) { super.paintComponent(g); g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); }
    }

    class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) { super(); this.radius = radius; setOpaque(false); }
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
    
    class RoundedTextFieldBase extends JTextField {
        private final int radius = 20;
        public RoundedTextFieldBase() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
        @Override
        protected void paintComponent(Graphics g) { g.setColor(Color.WHITE); g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); super.paintComponent(g); }
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    class RoundedPasswordFieldBase extends JPasswordField {
        private final int radius = 20;
        public RoundedPasswordFieldBase() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
        @Override
        protected void paintComponent(Graphics g) { g.setColor(Color.WHITE); g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); super.paintComponent(g); }
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
    
    class PlaceholderTextField extends RoundedTextFieldBase {
        private final String placeholder;
        public PlaceholderTextField(String placeholder) { super(); this.placeholder = placeholder; }
        @Override
        protected void paintComponent(final Graphics pG) {
            super.paintComponent(pG);
            if (getText().isEmpty()) {
                final Graphics2D g = (Graphics2D) pG.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(getDisabledTextColor());
                g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
                g.dispose();
            }
        }
    }
    
    class PlaceholderPasswordField extends RoundedPasswordFieldBase {
        private final String placeholder;
        public PlaceholderPasswordField(String placeholder) { super(); this.placeholder = placeholder; }
        @Override
        protected void paintComponent(final Graphics pG) {
            super.paintComponent(pG);
            if (getPassword().length == 0) {
                final Graphics2D g = (Graphics2D) pG.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(getDisabledTextColor());
                g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
                g.dispose();
            }
        }
    }
    
    // Main de prueba
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignUpView view = new SignUpView();
            view.setVisible(true);
        });
    }
}
