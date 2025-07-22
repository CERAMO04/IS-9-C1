package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class RechargeView extends JFrame {

    private WalletView walletView;
    private JButton mainPageButton, costButton, logButton, confirmButton;
    private JTextField amountField, refField;
    User user = User.getInstance();

    public RechargeView(WalletView walletView) {
        setTitle("Recarga de Saldo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.walletView = walletView;
        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel mainPanel = new BackgroundPanel(bgImage);
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createTopNavBar(), BorderLayout.NORTH);
        mainPanel.add(createMainContentPanel(), BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
    
    // <<< CAMBIO CLAVE: Implementamos la técnica del "Panel Envoltorio" >>>
    private JComponent createMainContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(30, 0));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 50, 50, 50));
        
        contentPanel.add(new LeftSidePanel(false), BorderLayout.WEST);
        
        //  panel de pago.
        PaymentInfoPanel paymentPanel = new PaymentInfoPanel();
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(paymentPanel);
        contentPanel.add(wrapperPanel, BorderLayout.CENTER); 
        return contentPanel;
    }

    class PaymentInfoPanel extends RoundedPanel {
        public PaymentInfoPanel() {
            super(40);
            setBackground(new Color(255, 255, 255, 180));
            setBorder(new EmptyBorder(30, 40, 30, 40));
            setLayout(new BorderLayout(10, 20));
            setPreferredSize(new Dimension(800, 500)); 

            JLabel titleLabel = new JLabel("Información de Recarga (Pago Móvil)", SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
            add(titleLabel, BorderLayout.NORTH);

            JPanel formPanel = new JPanel(new GridLayout(0, 2, 15, 15));
            formPanel.setOpaque(false);

            Font labelFont = new Font("SansSerif", Font.BOLD, 16); // Fuente para las etiquetas ("Banco:", "Cédula:", etc.)
            Font valueFont = new Font("SansSerif", Font.PLAIN, 16); // Fuente para los valores ("Banesco", etc.)

            // Banco
            JLabel bankLabel = new JLabel("Banco:");
            bankLabel.setFont(labelFont);
            formPanel.add(bankLabel);
            
            JLabel bankValue = new JLabel("Banesco");
            bankValue.setFont(valueFont); 
            formPanel.add(bankValue);
            
            // Cédula
            JLabel idLabel = new JLabel("RIF:");
            idLabel.setFont(labelFont);
            formPanel.add(idLabel);

            JLabel idValue = new JLabel("J-30946974");
            idValue.setFont(valueFont); 
            formPanel.add(idValue);

            // Número
            JLabel phoneLabel = new JLabel("Número:");
            phoneLabel.setFont(labelFont);
            formPanel.add(phoneLabel);

            JLabel phoneValue = new JLabel("04242984190");
            phoneValue.setFont(valueFont); 
            formPanel.add(phoneValue);

            // Separador
            formPanel.add(new JSeparator());
            formPanel.add(new JSeparator());
            
            JLabel amountLabel = new JLabel("Monto a Recargar (Bs.):");
            amountLabel.setFont(labelFont);
            formPanel.add(amountLabel);

            amountField = new JTextField();
            amountField.setFont(valueFont);
            formPanel.add(amountField);
            
            JLabel refLabel = new JLabel("Número de Referencia (Ultimos 4 digitos):");
            refLabel.setFont(labelFont);
            formPanel.add(refLabel);

            refField = new JTextField();
            refField.setFont(valueFont);
            formPanel.add(refField);
            
            add(formPanel, BorderLayout.CENTER);
            
            confirmButton = new JButton("Confirmar Recarga");
            confirmButton.setFont(new Font("SansSerif", Font.BOLD, 18));
            confirmButton.setBackground(new Color(60, 179, 113));
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFocusPainted(false);
            confirmButton.setBorder(new EmptyBorder(15, 0, 15, 0));

            add(confirmButton, BorderLayout.SOUTH);
        }
    }

    private JComponent createTopNavBar() {
        RoundedPanel navBar = new RoundedPanel(30);
        navBar.setBackground(new Color(255, 255, 255, 180));
        navBar.setLayout(new BorderLayout(20, 0));
        navBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        Image logoImg = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        navBar.add(logoLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 0));
        rightPanel.setOpaque(false);
        
        rightPanel.add(walletView);

        User user = User.getInstance();
        
        if (user.getIsAdmin()){
            mainPageButton = createNavButton("Página Principal");
            costButton = createNavButton("Gestión de costos");
            rightPanel.add(mainPageButton);
            rightPanel.add(costButton);
        } else {
            mainPageButton = createNavButton("Página Principal");
            rightPanel.add(mainPageButton);
            costButton = null;
        }
        logButton = createNavButton("Cerrar sesión");
        rightPanel.add(logButton);

        navBar.add(rightPanel, BorderLayout.CENTER);

        JPanel container = new JPanel(new FlowLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.add(navBar);
        return container;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        return button;
    }

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage; public BackgroundPanel(Image image) { this.backgroundImage = image; } @Override protected void paintComponent(Graphics g) { super.paintComponent(g); g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); }
    }

    class RoundedPanel extends JPanel {
        private final int radius; public RoundedPanel(int radius) { super(); this.radius = radius; setOpaque(false); } @Override protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(getBackground()); g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); g2.dispose(); super.paintComponent(g); }
    }
    /*Getters */
    public JButton getMainPageButton(){return mainPageButton;}
    public JButton getLogButton(){return logButton;}
    public JButton getCostButton(){return costButton;}
    public JButton getConfirmButton(){return confirmButton;}
    public JTextField getAmountField(){return amountField;}
    public JTextField getRefField(){return refField;}
}