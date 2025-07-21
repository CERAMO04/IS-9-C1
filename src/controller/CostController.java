package controller;


import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import view.CostManagementView;
import model.CCB;
import model.Cost;
import model.User;
import model.persistence.CostFile;
import model.persistence.CCBFile;

public class CostController {
    //Atrib
    private CostManagementView view;
    private MainController mainController;
    private CostFile costFile = new CostFile();
    private CCBFile ccbFile = new CCBFile();
    //Funcion privada para refrescar la tabla de costos.
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
    //Funcion para calcular CCB
    private double calculateCCB(double trayNumber, double merma) {                          
        double CF = costFile.getAllFixedCost();
        double CV = costFile.getAllVariableCost();
        return ((CF + CV) / trayNumber * (1 + (merma/100)));
    }
    //Funcion para cargar el CCB al momento de abrir la interfaz
    private void loadCCB(CostManagementView view){
        CCB currentCCB = ccbFile.readCCB();

        if (currentCCB != null) {
            view.setCCB(currentCCB.getValueCCB(), currentCCB.getStartDate(), currentCCB.getEndDate());
        } else {
            view.setCCB(0.0, "N/A", "N/A");
        }
    }
    //COnstructor del controlador, Recibe una vista de cost y el mainController por parametro.
    public CostController(CostManagementView view, MainController mainController){
        this.view = view;
        this.mainController = mainController;

        loadCCB(view);
        refreshTable();                                                                     //Se refresca la tabla antes de iniciar.

        view.getAddButton().addActionListener(e -> {
            String selectedCategory = view.getComboCategorias().getSelectedItem().toString();   //Escuchamos boton para añadir costos
            DefaultTableModel model = view.getTableModel();
            model.addRow(new Object[]{selectedCategory, "", "", ""});
        });
        view.getSaveButton().addActionListener(e -> {                                      //Escuchamos boton para guardar la tabla modificada.
            if (view.getCostTable().isEditing()) {                                              //Funcion para poder guardar aunque el usuario tenga el click en una Celda de la tabla
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
        view.getRefreshButton().addActionListener(e ->{                                    //Escuchamos el boton Actualizar
            refreshTable();
        });
        view.getCalculateCCBButton().addActionListener(e ->{                               //Escuchamos el boton de Calcular CCB
            if (view.getTableVisible()){                                                   //Si el ScrollPanel es visible entonces cambiamos
                view.setVisibleTable(false);                                        //A la seccion para introducir fecha,merma,NB
                view.setVisibleCCB(true);
            }else{                                                                         //Si no es visible entonces es porqe ya hemos introducido
                String text = view.getNumberOfTraysField().getText().trim();               //Los datos de merma NB, y fecha por lo que al volverle a dar
                double trayNumber = Double.parseDouble(text);                              //Click en el boton procedera a hacer el calculo 
                String mermaText = view.getMermaPercentageField().getText().trim();        //Y una vez haya hecho el calculo acutalizara el CCB
                double merma = Double.parseDouble(mermaText);                              //Y luego hara el cambio de layout 
            try{
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
                double CCB = calculateCCB(trayNumber, merma);
                String startDate = view.getStartDateField().getText().trim();
                String endDate = view.getEndDateField().getText().trim();

                ccbFile.saveCCB(new CCB(CCB, startDate, endDate));
                view.setCCB(CCB,startDate,endDate);
                
            }catch (NumberFormatException ex) {
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
            view.setVisibleCCB(false);    
            view.setVisibleTable(true);
            }

        });
        view.getLogOutButton().addActionListener(e -> {                                         //Esuchcamos boton para cerrar sesion
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "Nos vemos pronto!");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });
        view.getMainButton().addActionListener(e -> {                                           //Escuchamos boton para regresar al Main Menu
            mainController.exitFrame(view);
            mainController.showMenu();
        });
    }
}