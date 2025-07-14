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
        setSize(520, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel superior con ComboBox y botón    
        String[] category = {"Fijo", "Variable"};
        comboCcategory = new JComboBox<>(category);                         //Vector de los tipos de categoria

        saveButton = new JButton("Guardar Cambios");
        addButton = new JButton("Agregar Costo");
        refeshButton = new JButton("Actualizar");

        panelSuperior.add(new JLabel("Categoría:"));
        panelSuperior.add(comboCcategory);
        panelSuperior.add(addButton);
        panelSuperior.add(saveButton);
        panelSuperior.add(refeshButton);   

        String[] columnas = {"Categoría", "Tipo", "Nombre", "Valor"};       // Modelo de la tabla
        Object[][] datos = {
            {"Fijo", "Cocina", "Gas", "120.00"},
            {"Fijo", "Instalación", "Agua", "90.00"}
        };

        tableModel = new DefaultTableModel(datos, columnas) {               //Funcion para editar los campos de la excel
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        costTable = new JTable(tableModel);                                 //tabla
        costTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(costTable);                //Permite el scroll
        


        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    /*Getters */
    public JComboBox<String> getComboCategorias() {return comboCcategory;}
    public JButton getAddButton() {return addButton;}
    public JTable getCostTable() {return costTable;}
    public DefaultTableModel getTableModel() {return tableModel;}
    public JButton getSaveButton(){ return saveButton;}
    public JButton getRefrechButton(){ return refeshButton;}
}
