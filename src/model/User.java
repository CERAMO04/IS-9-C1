package model;

public class User {

    // Singleton instance
    private static User instance;

    /*Atrib */
    private String name,lastName,userName,password,email,userType,ID;
    private boolean isAdmin;
    private Wallet wallet;

    /*Builders. */
    private User(String name, String lastName,String ID, String email,String password, String userName, double value, String userType){
        this.name = name;
        this.lastName = lastName;
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.isAdmin = false;
        this.wallet = new Wallet(this, value);

    }
    private User(String userName, String password, String ID) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.name = "";
        this.lastName = "";
        this.email = "";
        this.userType = "unknown";
        this.isAdmin = false;
        this.wallet = null; // aún no asignamos Wallet
    }

    public static synchronized void init(String name, String lastName,String ID, String email, String password, String username, double value,String userType) {
        if (instance == null) {
            instance = new User(name, lastName, ID, email,password,username,value,userType);
        }
    }
    public static synchronized void init(String user, String password, String ID) {
        if (instance == null) {
            instance = new User(user,password,ID);
        }
    }
    public static synchronized User getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Usuario no inicializado. Llama a init(...) primero.");
        }
        return instance;
    }
    // Clear instance (logout)
    public static synchronized void clearInstance() {
        instance = null;
    }

    /*Setters. */
    public void setName(String name) {this.name = name;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setID(String ID) {this.ID = ID;}
    public void setUser(String user) {this.userName = user;}
    public void setPassWord(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setUserType(String userType) {this.userType = userType;}
    public void setIsAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}
    /*Getters. */
    public Wallet getWallet() {return this.wallet;}
    public String getName() {return this.name;}
    public String getLastName() {return this.lastName;}
    public String getID() {return this.ID;}
    public String getUser() {return this.userName;}
    public String getPassword() {return this.password;}
    public String getEmail() {return this.email;}
    public String getUserType() {return this.userType;}
    public boolean getIsAdmin() {return this.isAdmin;}
    /*Functions */
    public void createWallet(){this.wallet = new Wallet(this);}
}