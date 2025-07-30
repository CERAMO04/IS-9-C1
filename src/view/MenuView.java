package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class MenuView extends JFrame {

    private JButton mainPageButton, costButton, logButton, rechargeButton, editButton;
    private WalletView walletView;
    private MealCardPanel breakfastPanel, lunchPanel;


    public MenuView(WalletView walletView) {
        setTitle("Menú del Comedor");
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
            editButton = createNavButton("Editar menú");
            rightPanel.add(mainPageButton);
            rightPanel.add(costButton);
            rightPanel.add(editButton);
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

    private JComponent createMainContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(30, 0));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 50, 50, 50));
        
        LeftSidePanel leftPanel = new LeftSidePanel(true);
        rechargeButton = leftPanel.getRechargeButton();
        contentPanel.add(leftPanel, BorderLayout.WEST);

        DailyMenuPanel menuPanel = new DailyMenuPanel();
        contentPanel.add(menuPanel, BorderLayout.CENTER); 

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

            breakfastPanel = new MealCardPanel("Desayuno", "/assets/desayuno.png", "");
            lunchPanel = new MealCardPanel("Almuerzo", "/assets/almuerzo.png", "");
            gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridx = 0; gbc.weightx = 0.5; gbc.weighty = 0.9; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; add(breakfastPanel, gbc);
            gbc.gridx = 1; add(lunchPanel, gbc);
        }
    }

    public class MealCardPanel extends RoundedPanel {
        private JLabel descriptionLabel;
        private JTextArea editTextArea;
        private boolean isEditMode = false;

        public MealCardPanel(String title, String imagePath, String description) {
            super(30);
            setLayout(new BorderLayout(0, 15));
            setBackground(new Color(245, 245, 245, 230));
            setBorder(new EmptyBorder(15, 15, 15, 15));
            setPreferredSize(new Dimension(300, 400));

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            titleLabel.setForeground(Color.DARK_GRAY);
            add(titleLabel, BorderLayout.NORTH);

            try {
                Image mealImg = new ImageIcon(getClass().getResource(imagePath)).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(mealImg));
                add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                add(new JLabel("Imagen no encontrada", SwingConstants.CENTER), BorderLayout.CENTER);
            }

            // Descripción normal
            descriptionLabel = new JLabel();
            descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            descriptionLabel.setForeground(Color.GRAY);
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Campo editable
            editTextArea = new JTextArea();
            editTextArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
            editTextArea.setLineWrap(true);
            editTextArea.setWrapStyleWord(true);
            editTextArea.setVisible(false); // Oculto al inicio
            add(editTextArea, BorderLayout.SOUTH);

            setDescription(description); // Aplica contenido inicial
        }

        public void setDescription(String newText) {
            String html = "<html><div style='text-align: center;'>" + newText + "</div></html>";
            descriptionLabel.setText(html);
            editTextArea.setText(newText); // Para prellenar si se activa edición
            if (!isEditMode) {
                add(descriptionLabel, BorderLayout.SOUTH);
                remove(editTextArea);
            }
        }

        public String getEditedText() {
            return editTextArea.getText();
        }

        public void setEditMode(boolean edit) {
            isEditMode = edit;
            if (edit) {
                remove(descriptionLabel);
                add(editTextArea, BorderLayout.SOUTH);
                editTextArea.setVisible(true);
            } else {
                remove(editTextArea);
                add(descriptionLabel, BorderLayout.SOUTH);
                setDescription(editTextArea.getText()); // Actualiza texto normal
            }
            revalidate();
            repaint();
        }
    }




    class BackgroundPanel extends JPanel {
        private final Image backgroundImage; public BackgroundPanel(Image image) { this.backgroundImage = image; } @Override protected void paintComponent(Graphics g) { super.paintComponent(g); g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); }
    }

    class RoundedPanel extends JPanel {
        private final int radius; public RoundedPanel(int radius) { super(); this.radius = radius; setOpaque(false); } @Override protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(getBackground()); g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); g2.dispose(); super.paintComponent(g); }
    }

    /* Getters */
    public JButton getMaiButton(){return mainPageButton;}
    public JButton getLogOutButton(){return logButton;}
    public JButton getCostButton(){return costButton;}
    public JButton getRechargeButton() { return rechargeButton;}
    public MealCardPanel getBreakfastPanel() { return breakfastPanel; }
    public MealCardPanel getLunchPanel() { return lunchPanel; }
    public JButton getEditButton() { return editButton;}
}
