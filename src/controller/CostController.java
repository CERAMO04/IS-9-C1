package controller;

import view.CostManagementView;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cost;
import model.User;
import model.persistence.CostFile;

public class CostController {
    private CostManagementView view;
    private MainController mainController;
    private CostFile costFile = new CostFile();

    private void refreshTable(){
            List<Cost> updatedCosts = costFile.readData();

            DefaultTableModel model = view.getTableModel();
            
            model.setRowCount(0);

            for (Cost cost : updatedCosts) {
                Object[] rowData = {
                    cost.getCategory(),
                    cost.getType(),
                    cost.getName(),
                    cost.getCost()
                };
                model.addRow(rowData);
            }
        }
    public CostController(CostManagementView view, MainController mainController){
        this.view = view;
        this.mainController = mainController;

        refreshTable();

        view.getAddButton().addActionListener(e -> {
            String selectedCategory = view.getComboCategorias().getSelectedItem().toString();
            DefaultTableModel model = view.getTableModel();
            model.addRow(new Object[]{selectedCategory, "", "", ""});
        });
        view.getSaveButton().addActionListener(e -> {
            if (view.getCostTable().isEditing()) {
                view.getCostTable().getCellEditor().stopCellEditing();
            }
            DefaultTableModel model = view.getTableModel();
            int rowCount = model.getRowCount();
            ArrayList<Cost> costsToSave = new ArrayList<>();

            for (int i=0; i < rowCount; i++){
                String category = model.getValueAt(i, 0).toString();
                String type = model.getValueAt(i, 1).toString(); 
                String name = model.getValueAt(i, 2).toString(); 
                String valueText = model.getValueAt(i, 3).toString();
                try {
                    double value = Double.parseDouble(valueText);
                    Cost cost = new Cost(type, category, name, value);
                    costsToSave.add(cost);
                }catch (NumberFormatException ex) {
                    //System.out.println("Valor inválido en la fila " + (i+1) + ": " + valueText);
                    view.getCostTable().setRowSelectionInterval(i, i);
                    JOptionPane.showMessageDialog(view,
                        "Valor inválido en fila " + (i+1) + ": " + valueText,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    
                    view.getRefreshButton().doClick();
                    return;
                }
            }
            costFile.saveAll(costsToSave);
        });

        view.getRefreshButton().addActionListener(e ->{
            refreshTable();
        });
        view.getcalcButton().addActionListener(e ->{
            try {
                String text = view.getstraysField().getText().trim();
                double trayNumber = Double.parseDouble(text);
                String mermaText = view.getMermaField().getText().trim();
                double merma = Double.parseDouble(mermaText);

                if (Double.isNaN(trayNumber)) {
                    throw new NumberFormatException("Número de bandejas no válido");
                }
                
                if (Double.isNaN(merma)) {
                    throw new NumberFormatException("Porcentaje de merma no válido");
                }

            if (trayNumber <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "El número de bandejas debe ser mayor que cero", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (merma < 0) {
                JOptionPane.showMessageDialog(view,
                    "El porcentaje de merma no puede ser negativo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

                double costPerTray = calculateCCB(trayNumber, merma);
                view.setCalculatedCCB(costPerTray);
            } catch (NumberFormatException ex) {
                //JOptionPane.showMessageDialog(view, "Por favor, ingresa un número válido de bandejas.", "Error", JOptionPane.ERROR_MESSAGE);
                String errorMessage;
                if (ex.getMessage() != null && ex.getMessage().contains("merma")) {
                    errorMessage = "Porcentaje de merma no válido (debe ser un número)";
                } else {
                    errorMessage = "Número de bandejas no válido (debe ser un número)";
                }
                
                JOptionPane.showMessageDialog(view, 
                    errorMessage, 
                    "Error en formato", 
                    JOptionPane.ERROR_MESSAGE);
                    }
        });
        view.getLogOutButton().addActionListener(e -> {
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Nos vemos pronto!");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });
        view.getMainButton().addActionListener(e -> {
            mainController.exitFrame(view);
            mainController.showMenu();
        });
    }
    private double calculateCCB(double trayNumber, double merma) {
    double CF = costFile.getAllFixedCost();
    double CV = costFile.getAllVariableCost();
    
    // Merma is now passed as parameter (0-100 scale)
    return ((CF + CV) / trayNumber * (1 + (merma/100)));
}





}