package controller;

import model.User;
import model.Wallet;
import model.persistence.UserFile;
import view.WalletView;

public class WalletController {
    //Atrib
    private final WalletView walletView;
    public WalletController(WalletView walletView) {
        this.walletView = walletView;
    }
    // Depositar dinero
    public boolean deposit(double amount) {
        User user = User.getInstance();
        Wallet wallet = user.getWallet();
        if (amount > 0) {
            wallet.deposit(amount);
            updateView();
            return true;
        }
        return false;
    }
    public boolean withdraw(double amount) {
        User user = User.getInstance();
        Wallet wallet = user.getWallet();
        boolean success = wallet.withdraw(amount);
        if (success) {
            updateView();
        }
        return success;
    }
    public void updateView() {
        walletView.refreshBalance();
    }

    //Controlador creado para hacer funcionalidad de botones aqui
}