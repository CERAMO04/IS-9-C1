package view;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.*;

public class SignInView extends JFrame {

    private RoundedTextField userNameField;
    private RoundedPasswordField passwordField;
    private JButton loginButton, scanButton;
    private JLabel messageAlert, signUpLabel;

    public SignInView() {
        // ... (El constructor principal no cambia, todo está perfecto aquí)
        setTitle("Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel bgPanel = new BackgroundPanel(bgImage);
        bgPanel.setLayout(new GridBagLayout());
        RoundedPanel centerPanel = new RoundedPanel(40);
        centerPanel.setPreferredSize(new Dimension(350, 480));
        centerPanel.setBackground(new Color(255, 255, 255, 180));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel logo = new JLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        Image logoImg = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage();
        logoImg = logoImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoImg));
        userNameField = new RoundedTextField("Nombre de usuario");
        passwordField = new RoundedPasswordField("Contraseña");
        loginButton = new JButton("Acceder");
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        messageAlert = new JLabel("");
        messageAlert.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageAlert.setForeground(Color.RED);
        signUpLabel = new JLabel("Regístrate");
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpLabel.setForeground(Color.DARK_GRAY);
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel signUpText = new JLabel("¿No estas registrado?");
        signUpText.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpText.setForeground(Color.DARK_GRAY);

        //Boton de preuba 

        scanButton = new JButton("⚙");
        scanButton.setPreferredSize(new Dimension(20, 20));
        scanButton.setMaximumSize(new Dimension(20, 20));
        scanButton.setFocusPainted(false);
        scanButton.setBorderPainted(false);
        scanButton.setContentAreaFilled(false);
        scanButton.setForeground(Color.GRAY);
        scanButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        scanButton.setToolTipText("Botón de prueba");

        //Panel de prueba luego se quita
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setOpaque(false);
        buttonWrapper.setLayout(new BorderLayout());
        buttonWrapper.add(scanButton, BorderLayout.EAST);
        centerPanel.add(buttonWrapper);



        centerPanel.add(logo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(userNameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(signUpText);
        centerPanel.add(signUpLabel);
        centerPanel.add(messageAlert);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bgPanel.add(centerPanel, new GridBagConstraints());
        setContentPane(bgPanel);
    }

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;
        public BackgroundPanel(Image image) { this.backgroundImage = image; }
        @Override
        protected void paintComponent(Graphics g) { super.paintComponent(g); g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); }
    }
    class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) { this.radius = radius; setOpaque(false); }
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

    class RoundedTextField extends JTextField {
        private final int radius = 20;
        private final String placeHolder;
        private boolean showingPlaceholder = true;

        public RoundedTextField(String placeHolder) {
            // Llama al constructor vacío.
            super();
            
            this.placeHolder = placeHolder;
            
            setText(this.placeHolder);

            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setForeground(Color.GRAY);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (showingPlaceholder) {
                        setText("");
                        setForeground(Color.BLACK);
                        showingPlaceholder = false;
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(placeHolder);
                        setForeground(Color.GRAY);
                        showingPlaceholder = true;
                    }
                }
            });
        }
        
        public boolean isShowingPlaceholder() {
            return showingPlaceholder;
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

    // --- Y TAMBIÉN AQUÍ ---
    class RoundedPasswordField extends JPasswordField {
        private final int radius = 20;
        private final String placeHolder;
        private boolean showingPlaceholder = true;

        public RoundedPasswordField(String placeHolder) {
            super();
            
            this.placeHolder = placeHolder;
            
            setText(this.placeHolder);
            
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setForeground(Color.GRAY);
            setEchoChar((char) 0);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (showingPlaceholder) {
                        setText("");
                        setForeground(Color.BLACK);
                        setEchoChar('•');
                        showingPlaceholder = false;
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getPassword().length == 0) {
                        setText(placeHolder);
                        setForeground(Color.GRAY);
                        setEchoChar((char) 0);
                        showingPlaceholder = true;
                    }
                }
            });
        }

        public boolean isShowingPlaceholder() {
            return showingPlaceholder;
        }

        @Override
        protected void paintComponent(Graphics g) { 
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
    
    /*Getters y Setters sin cambios*/
    public JButton getScanButton(){return scanButton;}
    public JTextField getUserName(){ return userNameField;}
    public JPasswordField getPasswordField(){ return passwordField;}
    public JButton getSignInButton(){return loginButton;}
    public JLabel getSingUpLabel(){return signUpLabel;}
    public void setMessageAlert(String alert){ messageAlert.setText(alert); }
    public static void main(String[] args) { SwingUtilities.invokeLater(SignInView::new); }
}