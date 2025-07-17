package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CostManagementView extends JFrame {

    private JComboBox<String> comboCcategory;
    private JButton addButton;
    private JButton saveButton, refeshButton;
    private JTable costTable;
    private DefaultTableModel tableModel;

    public CostManagementView() {
        setTitle("Resumen de Costos");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con imagen de fondo
        JPanel contentPane = new JPanel() {
            Image background = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Aumenté el margen superior inicial

        // Panel de selección de categoría con esquinas redondeadas
        JPanel topPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(250, 50));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JLabel categoryLabel = new JLabel("Categoría:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 15));

        String[] category = {"Fijo", "Variable"};
        comboCcategory = new JComboBox<String>(category) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo blanco
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Texto negro
                g2.setColor(Color.BLACK);
                g2.setFont(getFont());
                String text = getSelectedItem() != null ? getSelectedItem().toString() : "";
                FontMetrics fm = g2.getFontMetrics();
                int x = 5; // Pequeño margen izquierdo
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);
                
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Borde gris claro
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };

        comboCcategory.setOpaque(false);
        comboCcategory.setForeground(Color.BLACK);
        comboCcategory.setBackground(Color.WHITE);
        comboCcategory.setFont(new Font("Arial", Font.PLAIN, 14));
        comboCcategory.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        comboCcategory.setPreferredSize(new Dimension(120, 30));
        
        comboCcategory.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setOpaque(true);
                setForeground(Color.BLACK);
                setBackground(Color.WHITE);
                if (isSelected) {
                    setBackground(new Color(230, 230, 230));
                }
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });

        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.insets = new Insets(0, 5, 0, 5);
        gbcTop.anchor = GridBagConstraints.WEST;
        
        gbcTop.gridx = 0;
        gbcTop.gridy = 0;
        topPanel.add(categoryLabel, gbcTop);
        
        gbcTop.gridx = 1;
        topPanel.add(comboCcategory, gbcTop);

        // Posicionamos el topPanel más arriba con mayor margen inferior
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 45, 40, 0); // Margen inferior aumentado a 40 para separación similar a la de los botones
        contentPane.add(topPanel, gbc);

        // Tabla
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 45, 40, 0); // Margen inferior de 40 (igual que entre tabla y botones)
        gbc.anchor = GridBagConstraints.CENTER;

        String[] columnas = {"Categoría", "Tipo", "Nombre", "Valor"};
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

        // Panel de botones
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setOpaque(false);

        addButton = createStyledButton("Agregar Costo");
        saveButton = createStyledButton("Guardar Cambios");
        refeshButton = createStyledButton("Actualizar");

        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(refeshButton);

        contentPane.add(bottomPanel, gbc);

        setVisible(true);
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
                    g2.setColor(new Color(255, 255, 255, 100));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 25, 25);
                }
                
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255));
                } else {
                    g2.setColor(new Color(240, 240, 240));
                }
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
            protected void paintBorder(Graphics g) {
            }
        };
        
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setPreferredSize(new Dimension(180, 45));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.repaint();
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.repaint();
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }

    public JComboBox<String> getComboCategorias() { return comboCcategory; }
    public JButton getAddButton() { return addButton; }
    public JTable getCostTable() { return costTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getRefrechButton() { return refeshButton; }
}
