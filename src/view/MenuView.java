package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.User;


public class MenuView extends JFrame {

    private WalletView walletView;
    public JButton mainPageButton, costButton, logButton;
    private JButton rechargeButton; // <<< CORRECCIÓN 1: Declarar la variable aquí.
    User user = User.getInstance();

    public MenuView() {
        setTitle("Menú del Comedor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel mainPanel = new BackgroundPanel(bgImage);
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createTopNavBar(), BorderLayout.NORTH);
        mainPanel.add(createMainContentPanel(), BorderLayout.CENTER);

        setContentPane(mainPanel);
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

        walletView = new WalletView();
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

    public WalletView getWalletStatusView() {
        return walletView;
    }

    private JComponent createMainContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(30, 0));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 50, 50, 50));
        
        //  Crea una instancia del NUEVO LeftSidePanel independiente.
        LeftSidePanel leftPanel = new LeftSidePanel(true);
        
        //  Le pide el botón de recarga.
        JButton rechargeBtn = leftPanel.getRechargeButton();

        
        if (rechargeBtn != null) {
            rechargeBtn.addActionListener(e -> {
                new RechargeView().setVisible(true);
                this.dispose(); // Cierra la ventana actual al abrir la de recarga.
            });
        }

        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(new DailyMenuPanel(), BorderLayout.CENTER); 
        return contentPanel;
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

    class DailyMenuPanel extends RoundedPanel {
        public DailyMenuPanel() {
            super(40); setLayout(new GridBagLayout()); setBackground(new Color(255, 255, 255, 180)); setBorder(new EmptyBorder(20, 40, 40, 40));
            JLabel title = new JLabel("Menú del Día", SwingConstants.CENTER); title.setFont(new Font("SansSerif", Font.BOLD, 36)); title.setBorder(new EmptyBorder(20, 0, 40, 0));
            GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.weightx = 1.0; gbc.weighty = 0.1; gbc.fill = GridBagConstraints.NONE; add(title, gbc);
            String breakfastDesc = "Empanada de carne mechada<br>Jugo de naranja<br>Manzana"; String lunchDesc = "Jugo de melón<br>Pabellón criollo<br>Mandarina";
            MealCardPanel breakfast = new MealCardPanel("Desayuno", "/assets/desayuno.png", breakfastDesc); MealCardPanel lunch = new MealCardPanel("Almuerzo", "/assets/almuerzo.png", lunchDesc);
            gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridx = 0; gbc.weightx = 0.5; gbc.weighty = 0.9; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; add(breakfast, gbc);
            gbc.gridx = 1; add(lunch, gbc);
        }
    }
    class MealCardPanel extends RoundedPanel {
        public MealCardPanel(String title, String imagePath, String description) {
            super(30); setBackground(new Color(245, 245, 245, 230)); setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(15, 15, 15, 15)); setPreferredSize(new Dimension(300, 400));
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER); titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); titleLabel.setForeground(Color.DARK_GRAY); add(titleLabel, BorderLayout.NORTH);
            try { Image mealImg = new ImageIcon(getClass().getResource(imagePath)).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); JLabel imageLabel = new JLabel(new ImageIcon(mealImg)); add(imageLabel, BorderLayout.CENTER); } catch (Exception e) { System.err.println("Error al cargar la imagen: " + imagePath); add(new JLabel("Imagen no encontrada", SwingConstants.CENTER), BorderLayout.CENTER); }
            String htmlDescription = "<html><div style='text-align: center;'>" + description + "</div></html>"; JLabel descriptionLabel = new JLabel(htmlDescription); descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); descriptionLabel.setForeground(Color.GRAY); descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER); add(descriptionLabel, BorderLayout.SOUTH);
        }
    }
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage; public BackgroundPanel(Image image) { this.backgroundImage = image; } @Override protected void paintComponent(Graphics g) { super.paintComponent(g); g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); }
    }
    class RoundedPanel extends JPanel {
        private final int radius; public RoundedPanel(int radius) { super(); this.radius = radius; setOpaque(false); } @Override protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(getBackground()); g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); g2.dispose(); super.paintComponent(g); }
    }

    /*Getters */
    public JButton getMaiButton(){return mainPageButton;}
    public JButton getLogOutButton(){return logButton;}
    public JButton getCostButton(){return costButton;}
    public JButton getRechargeButton() { return rechargeButton; }
    //public JButton logOutButton(){return }



    /*public static void main(String[] args) {
        try {
            User.init("28300189", "Carlos Estudiante", "passwordDePrueba123");
        } catch (Exception e) {
            System.err.println("No se pudo inicializar el usuario para la prueba: " + e.getMessage());
            return;
        }

        SwingUtilities.invokeLater(() -> {
            MenuView view = new MenuView();
            view.setVisible(true);
            view.getWalletStatusView().updateBalance(250.75); 
        });
    }*/
}