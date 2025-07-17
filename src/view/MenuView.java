package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.User;

import java.awt.*;

public class MenuView extends JFrame {

    private WalletView walletView;

    public MenuView() {
        // La configuración de la ventana debe ir primero
        setTitle("Gestor de Comedor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Luego, la configuración de los paneles y componentes
        Image bgImage = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
        BackgroundPanel mainPanel = new BackgroundPanel(bgImage);
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createTopNavBar(), BorderLayout.NORTH);
        mainPanel.add(createMainContentPanel(), BorderLayout.CENTER);

        setContentPane(mainPanel);
        // Es mejor que setVisible(true) lo llame el controlador o el main,
        // pero por ahora lo dejamos para que funcione.
        // setVisible(true); 
    }

    private JComponent createTopNavBar() {
        RoundedPanel navBar = new RoundedPanel(30);
        navBar.setBackground(new Color(255, 255, 255, 180));
        navBar.setLayout(new BorderLayout(20, 0));
        navBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        Image logoImg = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        navBar.add(logoLabel, BorderLayout.WEST);

        // Panel para los botones y el monedero
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 0));
        rightPanel.setOpaque(false);

        // ¡Aquí integramos la vista del monedero!
        walletView = new WalletView();
        rightPanel.add(walletView); // Lo añadimos primero a la derecha

        // Añadimos los botones de navegación después
        User user = User.getInstance();
        
        
        if (user.getIsAdmin()){
            rightPanel.add(createNavButton("Página principal"));
            rightPanel.add(createNavButton("Gestión de costos"));
        }else {
            rightPanel.add(createNavButton("Página principal"));     
        }

        rightPanel.add(createNavButton("Cerrar sesión"));

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
        contentPanel.add(new MenuManagerPanel(), BorderLayout.CENTER);
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
            
            // Es buena práctica seguir limitando la altura para que no se estiren
            profilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, profilePanel.getPreferredSize().height));
            calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendarPanel.getPreferredSize().height));

            // 1. Resorte superior: Ocupa el espacio de arriba
            add(Box.createVerticalGlue());

            // 2. Añadimos el contenido
            add(profilePanel);
            add(Box.createRigidArea(new Dimension(0, 30))); // Un espacio fijo entre ellos
            add(calendarPanel);
            
            // 3. Resorte inferior: Ocupa el espacio de abajo
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

            JLabel name = new JLabel("Nombre Apellido");
            name.setFont(new Font("SansSerif", Font.BOLD, 16));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel occupation = new JLabel("Ocupación");
            occupation.setForeground(Color.GRAY);
            occupation.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel dependency = new JLabel("Dependencia");
            dependency.setForeground(Color.GRAY);
            dependency.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(userIconLabel);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(name);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(occupation);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(dependency);
        }
    }
    
    class CalendarPanel extends RoundedPanel {
        public CalendarPanel() {
            super(30);
            setBackground(new Color(255, 255, 255, 180));
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            // Establecemos un tamaño preferido para que no sea tan grande por defecto
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
            
            // Cabeceras de los días como en la imagen de referencia
            String[] headers = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
            for (String header : headers) {
                daysPanel.add(new JLabel(header, SwingConstants.CENTER));
            }
            // Días del mes (ejemplo estático)
            for (int i = 1; i <= 31; i++) {
                JLabel day = new JLabel(String.format("%02d", i), SwingConstants.CENTER); // Formato con dos dígitos
                if (i >= 19 && i <= 21) {
                    day.setForeground(Color.RED);
                }
                daysPanel.add(day);
            }
            add(daysPanel, BorderLayout.CENTER);
        }
    }

    class MenuManagerPanel extends RoundedPanel {
        public MenuManagerPanel() {
            super(40);
            setLayout(new BorderLayout());
            setBackground(new Color(255, 255, 255, 180));
            setBorder(new EmptyBorder(20, 40, 40, 40));
            JLabel title = new JLabel("Gestor de menus", SwingConstants.CENTER);
            title.setFont(new Font("SansSerif", Font.BOLD, 36));
            title.setBorder(new EmptyBorder(20, 0, 40, 0));
            add(title, BorderLayout.NORTH);
            
            // Reducimos el espacio entre los botones
            JPanel buttonsGrid = new JPanel(new GridLayout(2, 2, 60, 60)); 
            buttonsGrid.setOpaque(false);
            buttonsGrid.add(new CircularButton("Nuevo Menu"));
            buttonsGrid.add(new CircularButton("Borrar Menu"));
            buttonsGrid.add(new CircularButton("Nuevo Turno"));
            buttonsGrid.add(new CircularButton("Borrar Turno"));
            add(buttonsGrid, BorderLayout.CENTER);
        }
    }

    class CircularButton extends JButton {
        public CircularButton(String text) {
            super(text);
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource("/assets/engranaje.png"));
                // Reducimos el tamaño del icono
                Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); 
                setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.err.println("Error: No se pudo cargar la imagen 'engranaje.png'");
            }
            
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setForeground(Color.DARK_GRAY);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            setVerticalTextPosition(SwingConstants.BOTTOM);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setIconTextGap(10); // Reducimos el espacio entre icono y texto
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int diameter = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2 - (getIconTextGap());

            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillOval(x + 3, y + 3, diameter, diameter);

            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, diameter, diameter);

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawOval(x, y, diameter - 1, diameter - 1);
            
            g2.dispose();
            super.paintComponent(g); 
        }
    }

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;
        public BackgroundPanel(Image image) { this.backgroundImage = image; }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) {
            super();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuView view = new MenuView();
            view.setVisible(true);

            // Ejemplo de cómo un controlador actualizaría el saldo
            // Puedes probar con valores positivos y negativos
            view.getWalletStatusView().updateBalance(-100); 
        });
    }
}
