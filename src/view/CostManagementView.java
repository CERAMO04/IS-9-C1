package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import model.User;

public class CostManagementView extends JFrame {
    //Atrib
    private JTextField mermaField, startDateField, endDateField, mermaPercentageField, numberOfTraysField;
    private JButton addButton, saveButton, refeshButton, calcCCBButton, mainPageButton, costButton, logOutButton;
    private JPanel ccbContainerPanel, calcInputPanel;
    private JScrollPane scrollPane; 
    private JTable costTable;
    private JLabel ccbValueLabel, ccbStartLabel, ccbEndLabel,ccbLabel;
    private JComboBox<String> comboCcategory;
    private DefaultTableModel tableModel;
    private WalletView walletView; 

    //Constructor del costManagement 
    public CostManagementView(WalletView walletView) {
        setTitle("Gestión y Resumen de Costos");
        setSize(1200, 800);
        setMinimumSize(new Dimension(1100, 750));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.walletView = walletView;
        //Panel de fondo con imagen
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

        // --- Barra de navegación superior ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPane.add(createTopNavBar(), gbc);

        // ---Finaliza bloque de codigo de barra de superior ---

        // --- Contenedor superior para categoría y CCB ---
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
        gbcTop.weightx = 1.0;
        gbcTop.anchor = GridBagConstraints.WEST;

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
        gbcTop.weightx = 0;
        gbcTop.anchor = GridBagConstraints.EAST;

        JPanel topRightPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 25);
                g2.dispose();
            }
        };
        topRightPanel.setOpaque(false);
        topRightPanel.setPreferredSize(new Dimension(300, 70));
        
        
        //Tengo problemas con este grid.
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcTop.gridx = 1;
        gbcTop.insets = new Insets(0, 30, 0, 0);
        gbcTop.weightx = 1.0;
        gbcTop.fill = GridBagConstraints.HORIZONTAL;
        gbcTop.anchor = GridBagConstraints.CENTER;

        // Panel vertical interno
        JPanel ccbInfoPanel = new JPanel();
        ccbInfoPanel.setOpaque(false);
        ccbInfoPanel.setLayout(new BoxLayout(ccbInfoPanel, BoxLayout.Y_AXIS));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = Color.WHITE;

        ccbLabel = new JLabel("Costo Cubierto por bandeja: ");
        ccbLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ccbLabel.setForeground(Color.WHITE);
        ccbLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ccbValueLabel = new JLabel("");
        ccbValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ccbValueLabel.setForeground(Color.WHITE);
        ccbValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ccbStartLabel = new JLabel("");
        ccbStartLabel.setFont(labelFont);
        ccbStartLabel.setForeground(labelColor);
        ccbStartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ccbEndLabel = new JLabel("");
        ccbEndLabel.setFont(labelFont);
        ccbEndLabel.setForeground(labelColor);
        ccbEndLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar al panel vertical
        ccbInfoPanel.add(ccbLabel);
        ccbInfoPanel.add(ccbValueLabel);
        ccbInfoPanel.add(Box.createVerticalStrut(4)); 
        ccbInfoPanel.add(ccbStartLabel);
        ccbInfoPanel.add(ccbEndLabel);

        topRightPanel.add(ccbInfoPanel, gbcRight);
        topContainerPanel.add(topRightPanel, gbcTop);

        contentPane.add(topContainerPanel, gbc);
        //--- Fin de barras superiores ---

        // --- Tabla de costos ---
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        String[] columnas = {"Categoría", "Tipo", "Nombre", "Valor"};
        Object[][] datos = {};

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

        scrollPane = new JScrollPane(costTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        contentPane.add(scrollPane, gbc);

        ccbContainerPanel = new RoundedPanel(40); 
        ccbContainerPanel.setBackground(new Color(255, 255, 255, 180)); 
        ccbContainerPanel.setLayout(new GridBagLayout()); 
        ccbContainerPanel.setPreferredSize(new Dimension(900, 400)); 
        ccbContainerPanel.setVisible(false); 

        calcInputPanel = new JPanel(new GridBagLayout());
        calcInputPanel.setOpaque(false); 

        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 10, 5, 10);
        gbcInput.anchor = GridBagConstraints.CENTER; 
        gbcInput.fill = GridBagConstraints.NONE; 
        gbcInput.weightx = 0; 
        gbcInput.weighty = 0; 

        // Panel Fecha Inicio (Primera fila, primera columna)
        JPanel startDatePanel = createInputPanel("Fecha Inicio:", true);
        startDateField = (JTextField) startDatePanel.getClientProperty("textField");
        gbcInput.gridx = 0;
        gbcInput.gridy = 0; 
        calcInputPanel.add(startDatePanel, gbcInput);

        // Panel Fecha Fin (Primera fila, segunda columna)
        JPanel endDatePanel = createInputPanel("Fecha Fin:", true);
        endDateField = (JTextField) endDatePanel.getClientProperty("textField");
        gbcInput.gridx = 1;
        gbcInput.gridy = 0; 
        calcInputPanel.add(endDatePanel, gbcInput);

        // Panel % de Merma (Segunda fila, primera columna)
        JPanel mermaPanel = createInputPanel("% de merma:", false);
        mermaPercentageField = (JTextField) mermaPanel.getClientProperty("textField");
        gbcInput.gridx = 0; 
        gbcInput.gridy = 1; 
        calcInputPanel.add(mermaPanel, gbcInput);

        // Panel # de Bandejas (Segunda fila, segunda columna)
        JPanel traysPanel = createInputPanel("# de bandejas:", false);
        numberOfTraysField = (JTextField) traysPanel.getClientProperty("textField");
        gbcInput.gridx = 1; 
        gbcInput.gridy = 1; 
        calcInputPanel.add(traysPanel, gbcInput);

        GridBagConstraints gbcCcbContainer = new GridBagConstraints();
        gbcCcbContainer.gridx = 0;
        gbcCcbContainer.gridy = 0;
        gbcCcbContainer.anchor = GridBagConstraints.CENTER; 
        ccbContainerPanel.add(calcInputPanel, gbcCcbContainer);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(ccbContainerPanel, gbc);

        // ===== BOTONES INFERIORES =====
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.fill = GridBagConstraints.NONE;

        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setPreferredSize(new Dimension(900, 60));
        bottomPanel.setOpaque(false);

        addButton = createStyledButton("Agregar Costo");
        saveButton = createStyledButton("Guardar Cambios");
        refeshButton = createStyledButton("Actualizar");
        calcCCBButton = createStyledButton("Calcular CCB");

        int panelWidth = 900;
        int buttonWidth = 180;
        int buttonHeight = 45;
        int spaceBetweenButtons = (panelWidth - (4 * buttonWidth)) / 3;

        addButton.setBounds(0, 5, buttonWidth, buttonHeight);
        saveButton.setBounds(addButton.getX() + buttonWidth + spaceBetweenButtons, 5, buttonWidth, buttonHeight);
        refeshButton.setBounds(saveButton.getX() + buttonWidth + spaceBetweenButtons, 5, buttonWidth, buttonHeight);
        calcCCBButton.setBounds(refeshButton.getX() + buttonWidth + spaceBetweenButtons, 5, buttonWidth, buttonHeight);

        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(refeshButton);
        bottomPanel.add(calcCCBButton);

        contentPane.add(bottomPanel, gbc);
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
        
        rightPanel.add(walletView);

        User user = null;
        try {
            User.init("001", "Admin User", "adminpass");
            user = User.getInstance();
            if (user != null) {
                user.setIsAdmin(true);
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar el usuario: " + e.getMessage());
        }

        if (user != null && user.getIsAdmin()){
            mainPageButton = createNavButton("Página Principal");
            costButton = createNavButton("Gestión de Costos");
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
 
    private JPanel createInputPanel(String labelText, boolean isDateField) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        
        panel.setPreferredSize(new Dimension(220, 50));
        panel.setMinimumSize(new Dimension(220, 50));
        panel.setMaximumSize(new Dimension(220, 50)); 

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(0, 5, 0, 5);
        gbcPanel.anchor = GridBagConstraints.CENTER;
        gbcPanel.fill = GridBagConstraints.NONE; 
        gbcPanel.weightx = 0; 
        gbcPanel.weighty = 0; 

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        gbcPanel.gridx = 0;
        panel.add(label, gbcPanel);

        JTextField textField = new JTextField(8);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(80, 30));

        // Sugerencia de formato para las fechas
        if (isDateField) {
            textField.setToolTipText("Formato: dd/MM/yyyy");
        }

        gbcPanel.gridx = 1;
        panel.add(textField, gbcPanel);
        panel.putClientProperty("textField", textField); 

        return panel;
    }

    /*Getters */
    public JComboBox<String> getComboCategorias() { return comboCcategory; }
    public JButton getAddButton() { return addButton; }
    public JTable getCostTable() { return costTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getRefreshButton() { return refeshButton; }
    public JButton getCalculateCCBButton() {return calcCCBButton;} 
    public JTextField getMermaField() { return mermaField;} 
    public JButton getMainButton(){return mainPageButton;}
    public JButton getLogOutButton(){return logOutButton;}
    public JButton calcCCBButton(){return calcCCBButton;}
    public JTextField getStartDateField() { return startDateField; }
    public JTextField getEndDateField() { return endDateField; }
    public JTextField getMermaPercentageField() { return mermaPercentageField; }
    public JTextField getNumberOfTraysField() { return numberOfTraysField; }

    /*Setters */
    public void setCCB(double newValue, String startDate, String endDate){
        ccbValueLabel.setText(String.format("%.2f", newValue));
        ccbStartLabel.setText("Dresde: " + startDate);
        ccbEndLabel.setText("Hasta: " + endDate);
    }
    public void setVisibleTable(boolean option){
        scrollPane.setVisible(option);
    }
    public void setVisibleCCB(boolean option){
        ccbContainerPanel.setVisible(option);
    }
    public boolean getTableVisible(){
        return scrollPane.isVisible();
    }
/* 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                
                User.init("001", "Admin User", "adminpass");
                User.getInstance().setIsAdmin(true);

                CostManagementView view = new CostManagementView();
                view.setVisible(true);

                if (view.getWalletStatusView() != null) {
                    view.getWalletStatusView().updateBalance(999.99);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error crítico al inicializar la aplicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
    */
}