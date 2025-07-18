package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.User;


public class MenuView extends JFrame {

    private WalletView walletView;
    public JButton mainPageButton, costButton, logButton;
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
            mainPageButton = createNavButton("Pagina Principal");
            costButton = createNavButton("Gestion de costos");
            rightPanel.add(mainPageButton);
            rightPanel.add(costButton);
        } else {
            mainPageButton = createNavButton("Pagina Principal");
            rightPanel.add(mainPageButton);
            costButton = null;
        }
        logButton = createNavButton("Cerra sesion");
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
        contentPanel.add(new LeftSidePanel(), BorderLayout.WEST);
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

    class LeftSidePanel extends JPanel {
        public LeftSidePanel() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            ProfilePanel profilePanel = new ProfilePanel();
            CalendarPanel calendarPanel = new CalendarPanel();
            profilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, profilePanel.getPreferredSize().height));
            calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendarPanel.getPreferredSize().height));
            add(Box.createVerticalGlue());
            add(profilePanel);
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(calendarPanel);
            add(Box.createVerticalGlue());
        }
    }
    
    class ProfilePanel extends RoundedPanel {
        public ProfilePanel() {
            super(30);
            setBackground(new Color(255, 255, 255, 180));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            setAlignmentX(Component.CENTER_ALIGNMENT);

            Image userImg = new ImageIcon(getClass().getResource("/assets/user.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel userIconLabel = new JLabel(new ImageIcon(userImg));
            userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            String fullName = user.getName() + " " + user.getLastName();
            JLabel name = new JLabel(fullName);
            name.setFont(new Font("SansSerif", Font.BOLD, 16));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel occupation = new JLabel(user.getUserType());
            occupation.setForeground(Color.GRAY);
            occupation.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(userIconLabel);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(name);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(occupation);
            add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }
    
    class CalendarPanel extends RoundedPanel {
        public CalendarPanel() {
            super(30);
            setBackground(new Color(255, 255, 255, 180));
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setPreferredSize(new Dimension(280, 280)); 
            JLabel monthLabel = new JLabel("DICIEMBRE", SwingConstants.CENTER);
            monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            monthLabel.setForeground(Color.WHITE);
            JPanel monthPanel = new JPanel();
            monthPanel.setBackground(new Color(150, 100, 200));
            monthPanel.add(monthLabel);
            add(monthPanel, BorderLayout.NORTH);
            JPanel daysPanel = new JPanel(new GridLayout(0, 7, 5, 5));
            daysPanel.setOpaque(false);
            daysPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
            String[] headers = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
            for (String header : headers) {
                daysPanel.add(new JLabel(header, SwingConstants.CENTER));
            }
            for (int i = 1; i <= 31; i++) {
                JLabel day = new JLabel(String.format("%02d", i), SwingConstants.CENTER);
                if (i >= 19 && i <= 21) {
                    day.setForeground(Color.RED);
                }
                daysPanel.add(day);
            }
            add(daysPanel, BorderLayout.CENTER);
        }
    }

    // =================================================================
    // =========== PANEL PRINCIPAL CORREGIDO ===========================
    // =================================================================
    class DailyMenuPanel extends RoundedPanel {
        public DailyMenuPanel() {
            super(40);
            setLayout(new GridBagLayout()); // Usamos GridBagLayout para mejor control
            setBackground(new Color(255, 255, 255, 180));
            setBorder(new EmptyBorder(20, 40, 40, 40));

            JLabel title = new JLabel("Menú del Día", SwingConstants.CENTER);
            title.setFont(new Font("SansSerif", Font.BOLD, 36));
            title.setBorder(new EmptyBorder(20, 0, 40, 0));

            // Añadimos el título al norte del panel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Ocupa ambas columnas
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.NONE;
            add(title, gbc);

            String breakfastDesc = "Empanada de carne mechada<br>Jugo de naranja<br>Manzana";
            String lunchDesc = "Jugo de melón<br>Pabellón criollo<br>Mandarina";

            MealCardPanel breakfast = new MealCardPanel("Desayuno", "/assets/desayuno.png", breakfastDesc);
            MealCardPanel lunch = new MealCardPanel("Almuerzo", "/assets/almuerzo.png", lunchDesc);

            // Añadimos el desayuno
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0.9;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            add(breakfast, gbc);

            // Añadimos el almuerzo
            gbc.gridx = 1;
            add(lunch, gbc);
            
        }
    }

    // =================================================================
    // =========== TARJETA DE COMIDA CORREGIDA =========================
    // =================================================================
    class MealCardPanel extends RoundedPanel {
        public MealCardPanel(String title, String imagePath, String description) {
            super(30);
            setBackground(new Color(245, 245, 245, 230));
            setLayout(new BorderLayout(0, 15));
            setBorder(new EmptyBorder(15, 15, 15, 15));
            setPreferredSize(new Dimension(300, 400)); // Damos un tamaño fijo para mejor proporción

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            titleLabel.setForeground(Color.DARK_GRAY);
            add(titleLabel, BorderLayout.NORTH);

            try {
                // Hacemos la imagen un poco más grande
                Image mealImg = new ImageIcon(getClass().getResource(imagePath)).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(mealImg));
                add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + imagePath);
                add(new JLabel("Imagen no encontrada", SwingConstants.CENTER), BorderLayout.CENTER);
            }

            String htmlDescription = "<html><div style='text-align: center;'>" + description + "</div></html>";
            JLabel descriptionLabel = new JLabel(htmlDescription);
            descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            descriptionLabel.setForeground(Color.GRAY);
            
            // --- CORRECCIÓN CLAVE ---
            // Forzamos al JLabel a centrarse horizontalmente dentro de su espacio.
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            add(descriptionLabel, BorderLayout.SOUTH);
        }
    }

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



    /*Getters */
    public JButton getMaiButton(){return mainPageButton;}
    public JButton getLogOutButton(){return logButton;}
    public JButton getCostButton(){return costButton;}
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