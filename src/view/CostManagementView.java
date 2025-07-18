package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.User; 
import java.awt.*;

public class CostManagementView extends JFrame {

    // --- Atributos de la clase ---
    private JComboBox<String> comboCcategory;
    private JButton addButton, saveButton, refeshButton,calcButton, mainPageButton, costButton, logOutButton;
    private JTable costTable;
    private JLabel ccbValueLabel;
    private JTextField traysField, mermaField;
    private DefaultTableModel tableModel;
    private WalletView walletView;

    public CostManagementView() {
        setTitle("Gestión y Resumen de Costos");
        setSize(1200, 800);
        setMinimumSize(new Dimension(1100, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new GridBagLayout()) {
            Image background = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPane.add(createTopNavBar(), gbc);

        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 1; 
        gbc.insets = new Insets(20, 0, 30, 0); 

        JPanel topContainerPanel = new JPanel(new GridBagLayout());
        topContainerPanel.setOpaque(false);
        topContainerPanel.setPreferredSize(new Dimension(900, 60));

        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.gridy = 0;
        gbcTop.gridx = 0;
        gbcTop.insets = new Insets(0, 0, 0, 30); 
        JPanel topLeftPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        topLeftPanel.setOpaque(false);
        topLeftPanel.setPreferredSize(new Dimension(300, 50));

        JLabel categoryLabel = new JLabel("Categoría:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 15));

        String[] category = {"Fijo", "Variable"};
        comboCcategory = new JComboBox<>(category);
        comboCcategory.setFont(new Font("Arial", Font.PLAIN, 14));
        comboCcategory.setPreferredSize(new Dimension(120, 30));

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(0, 10, 0, 10);
        gbcLeft.gridx = 0;
        topLeftPanel.add(categoryLabel, gbcLeft);
        gbcLeft.gridx = 1;
        topLeftPanel.add(comboCcategory, gbcLeft);

        topContainerPanel.add(topLeftPanel, gbcTop);

        gbcTop.gridx = 1;
        gbcTop.insets = new Insets(0, 30, 0, 0); 
        JPanel topRightPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        topRightPanel.setOpaque(false);
        // <<< CORRECCIÓN 1: Se restaura el tamaño original del panel para mantener el layout.
        topRightPanel.setPreferredSize(new Dimension(480, 50));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(0, 8, 0, 8);
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;

        JLabel ccbLabel = new JLabel("CCB:");
        ccbLabel.setForeground(Color.WHITE);
        ccbLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        ccbValueLabel = new JLabel("0.0");
        ccbValueLabel.setForeground(Color.WHITE);
        ccbValueLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel mermaLabel = new JLabel("% de merma:");
        mermaLabel.setForeground(Color.WHITE);
        mermaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mermaField = new JTextField();
        mermaField.setPreferredSize(new Dimension(100, 30));

        JLabel traysLabel = new JLabel("# de bandejas:");
        traysLabel.setForeground(Color.WHITE);
        traysLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        traysField = new JTextField();
        traysField.setPreferredSize(new Dimension(120, 30));

        calcButton = new JButton("Calcular");
        calcButton.setPreferredSize(new Dimension(100, 30));
        calcButton.setFocusPainted(false);
        calcButton.setFont(new Font("Arial", Font.BOLD, 13));

        gbcRight.gridx = 0; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0; topRightPanel.add(ccbLabel, gbcRight);
        gbcRight.gridx = 1; topRightPanel.add(ccbValueLabel, gbcRight);
        gbcRight.gridx = 2; topRightPanel.add(mermaLabel, gbcRight);
        gbcRight.gridx = 3; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 0.5; topRightPanel.add(mermaField, gbcRight);
        gbcRight.gridx = 4; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0; topRightPanel.add(traysLabel, gbcRight);
        gbcRight.gridx = 5; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 0.5; topRightPanel.add(traysField, gbcRight);
        gbcRight.gridx = 6; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0; topRightPanel.add(calcButton, gbcRight);

        topContainerPanel.add(topRightPanel, gbcTop);
        contentPane.add(topContainerPanel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);

        String[] columnas = {"Categoría", "Tipo", "Nombre", "Valor"};
        
        // <<< CORRECCIÓN 2: Se restauran todos los datos originales de la tabla.
        Object[][] datos = {
                {"Fijo", "Cocina", "Gas", "120.00"},
                {"Fijo", "Instalación", "Agua", "90.00"},
                {"Fijo", "Administración", "Sueldos", "50.45"},
                {"Fijo", "Equipo", "Mantenimiento", "75.25"},
                {"Fijo", "Instalación", "Electricidad", "88.9"},
                {"Variable", "Proteína", "Pollo", "132.5"},
                {"Variable", "Carbohidratos", "Arroz", "45.0"},
                {"Variable", "Lípidos", "Aceite", "62.3"},
                {"Variable", "Empaque", "Bandejas", "20.0"},
                {"Variable", "Limpieza", "Desinfectante", "30.0"},
        };

        tableModel = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        costTable = new JTable(tableModel);
        costTable.setFont(new Font("Arial", Font.PLAIN, 14));
        costTable.setRowHeight(32);
        costTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        costTable.setGridColor(new Color(200, 200, 200));
        costTable.setSelectionBackground(new Color(230, 230, 230));
        costTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(costTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        contentPane.add(scrollPane, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setOpaque(false);
        addButton = createStyledButton("Agregar Costo");
        saveButton = createStyledButton("Guardar Cambios");
        refeshButton = createStyledButton("Actualizar");
        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(refeshButton);
        contentPane.add(bottomPanel, gbc);
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

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        return button;
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
            costButton = createNavButton("Gestion de Costos");
            rightPanel.add(mainPageButton);
            rightPanel.add(costButton);
        } else {
            rightPanel.add(createNavButton("Página principal"));
            costButton = null;     
        }
        logOutButton = createNavButton("Cerrar sesión");
        rightPanel.add(logOutButton);

        navBar.add(rightPanel, BorderLayout.CENTER);

        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        container.setOpaque(false);
        container.add(navBar);
        return container;
    }

    public WalletView getWalletStatusView() {
        return walletView;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(50, 50, 50, 220));
                } else {
                    g2.setColor(new Color(0, 0, 0, 200));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                if (getModel().isRollover()) {
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);
                }
                g2.setColor(new Color(240, 240, 240));
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(0, 0, 0, 50));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                }
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setPreferredSize(new Dimension(180, 45));

        return button;
    }
    
    /*Getters */
    public JComboBox<String> getComboCategorias() { return comboCcategory; }
    public JButton getAddButton() { return addButton; }
    public JTable getCostTable() { return costTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getRefrechButton() { return refeshButton; }
    public JButton getcalcButton() {return calcButton;}
    public JTextField getstraysField() {return traysField;}
    public JTextField getMermaField() { return mermaField;}
    public JButton getMainButton(){return mainPageButton;}
    public JButton getLogOutButton(){return logOutButton;}



    /*Setters */
    public void setCalculatedCCB(double newValue){
        ccbValueLabel.setText(String.format("%.2f", newValue));
    }
    /*public static void main(String[] args) {
        try {
            User.init("001", "Admin User", "adminpass");
            User.getInstance().setIsAdmin(true); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error crítico al inicializar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            CostManagementView view = new CostManagementView();
            view.setVisible(true);

            if (view.getWalletStatusView() != null) {
                view.getWalletStatusView().updateBalance(999.99);
            }
        });
    }*/
}