package model;

public class User {
    /*Atrib */
    private String name,lastName,user,password ,email,userType;
    private boolean isAdmin;
    private Wallet wallet;
    /*Builders. */
    public User(String name, String lastName, String user, String password, String email, 
                String userType, boolean isAdmin){
                    this.wallet = new Wallet(this);
                    this.name = name;
                    this.lastName = lastName;
                    this.user = user;
                    this.password = password;
                    this.userType = userType;
                    this.isAdmin = isAdmin;
            }
    /*Setters. */
    public void setName(String name) {this.name = name;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setUser(String user) {this.user = user;}
    public void setPassWord(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setUserType(String userType) {this.userType = userType;}
    public void setIsAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}
    /*Getters. */
    public Wallet getWallet() {return this.wallet;}
    public String getName() {return this.name;}
    public String getLastName() {return this.lastName;}
    public String getUser() {return this.user;}
    public String getPassword() {return this.password;}
    public String getEmail() {return this.email;}
    public String getUserType() {return this.userType;}
    public boolean getIsAdmin() {return this.isAdmin;}
    /*Functions */

}