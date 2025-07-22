package controller;

import javax.swing.JOptionPane;
import model.User;
import model.persistence.UserFile;
import view.RechargeView;

public class RechargeController {
    private MainController mainController;
    private RechargeView view;
    private WalletController walletController;
    private UserFile userFile;
    private User user = User.getInstance();

    public RechargeController(RechargeView view, MainController mainController, WalletController walletController) {
        this.mainController = mainController;
        this.view = view;
        this.walletController = walletController;
        this.userFile = new UserFile(); 

        view.getConfirmButton().addActionListener(e -> {
            String text = view.getAmountField().getText().trim();
            String reff = view.getRefField().getText().trim();

            if (text.isEmpty() || reff.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
                return;
            }

            try {
                double value = Double.parseDouble(text);
                int ref = Integer.parseInt(reff);

                if (ref == 1234) {
                    double currentBalance = user.getWallet().getBalance();
                    double newBalance = currentBalance + value;

                    boolean success = walletController.deposit(newBalance);  

                    if (success) {
                        user.getWallet().deposit(newBalance); 
                        userFile.saveNewBalance(newBalance);     
                        walletController.updateView(); // actualizar vista
                        JOptionPane.showMessageDialog(view, "¡Su recarga fue realizada con éxito!");
                        mainController.exitFrame(view);
                        mainController.showMenu(); 
                    } else {
                        JOptionPane.showMessageDialog(view, "Error al realizar la recarga.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Referencia incorrecta.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Ingrese valores válidos.");
            }
        });


        view.getLogButton().addActionListener(e -> {
            User.clearInstance();
            JOptionPane.showMessageDialog(view, "¡Nos vemos pronto!");
            mainController.exitFrame(view);
            mainController.showLogIn();
        });

        view.getMainPageButton().addActionListener(e -> {
            mainController.exitFrame(view);
            mainController.showMenu();
        });

        view.getCostButton().addActionListener(e -> {
            mainController.exitFrame(view);
            mainController.showCostManagementView();
        });
    }
}
