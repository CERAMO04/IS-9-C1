package controller;

import model.User;
import model.Wallet;
import view.WalletView;

public class WalletController {
    private final WalletView walletView;
    
    public WalletController(WalletView walletView) {
        this.walletView = walletView;
    }

    // Depositar dinero
    public boolean deposit(double amount) {
        User user = User.getInstance(); //Referencia a Singleton creado durante inicio de sesion
        Wallet wallet = user.getWallet();
        
        if (amount > 0) {
            wallet.deposit(amount);
            return true;
        }
        return false;
    }

    // Retirar dinero
    public boolean withdraw(double amount) {
        User user = User.getInstance();
        Wallet wallet = user.getWallet();
        
        boolean success = wallet.withdraw(amount);
        return success;
    }

    // Get current balance
    public double getBalance() {
        return User.getInstance().getWallet().getBalance();
    }

    //Controlador creado para hacer funcionalidad de botones aqui
}