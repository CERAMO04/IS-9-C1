package controller;


import view.CostManagementView;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import model.Cost;
import model.persistence.CostFile;

public class CostController {
    private CostManagementView view;
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

    public CostController(CostManagementView view){
        this.view = view;

        refreshTable();

        view.getAddButton().addActionListener(e -> {
            String selectedCategory = view.getComboCategorias().getSelectedItem().toString();
            DefaultTableModel model = view.getTableModel();
            model.addRow(new Object[]{selectedCategory, "", "", ""});
        });
        /*view.getDeleteButton().addActionListener(e -> {                                               Debo pensar la logica de borrar
            String selectedCategory = view.getComboCategorias().getSelectedItem().toString();
            DefaultTableModel model = view.getTableModel();
            model.kick(new Object[]{selectedCategory, "", "", ""});
        });*/
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
                    System.out.println("Valor inválido en la fila " + (i+1) + ": " + valueText);
                }
            }
            costFile.saveAll(costsToSave);
        });

        view.getRefrechButton().addActionListener(e ->{
            refreshTable();
        });

    }

    /*private double calculateCCB(){
        double allFixedCost = costFile.getAllFixedCost();
        double allVariableCost =costFile.getAllVariableCost();
        double allExtraCost = costFile.getAllExtraCost();
        double result;






    }*/





}