package view;

import javax.swing.*;
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
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal con fondo
        JPanel contentPane = new JPanel() {
            Image background = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Panel superior con ComboBox
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false); // Transparente
        JLabel categoryLabel = new JLabel("Categoría:");
        categoryLabel.setForeground(Color.WHITE); // Texto blanco

        String[] category = {"Fijo", "Variable"};
        comboCcategory = new JComboBox<>(category);

        topPanel.add(categoryLabel);
        topPanel.add(comboCcategory);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        bottomPanel.setOpaque(false); // Transparente

        addButton = new JButton("Agregar Costo");
        saveButton = new JButton("Guardar Cambios");
        refeshButton = new JButton("Actualizar");

        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(refeshButton);

        // Tabla
        String[] columnas = {"Categoría", "Tipo", "Nombre", "Valor"};
        Object[][] datos = {
            {"Fijo", "Cocina", "Gas", "120.00"},
            {"Fijo", "Instalación", "Agua", "90.00"}
        };

        tableModel = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        costTable = new JTable(tableModel);
        costTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(costTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JComboBox<String> getComboCategorias() { return comboCcategory; }
    public JButton getAddButton() { return addButton; }
    public JTable getCostTable() { return costTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getRefrechButton() { return refeshButton; }
}
