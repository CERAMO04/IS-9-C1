package model;

public class Wallet {
    /*Atrb */
    private User ownerUser;
    private double balance;
    /*Builders */
    public Wallet(User ownUser){
        this.ownerUser = ownUser;
        this.balance = 0.0;
    }
    /*Getters */
    public double getBalance() {return this.balance;}
    public User getOUser() {return this.ownerUser;}
    /*Function */
    public void deposit(double amount) {
        if (amount <= 0) this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance < 0) return false;
        else {
            this.balance -= amount;
            return true;
        }
    }
}
