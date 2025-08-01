package model;

public class UserData {
    private String name, lastName, userName, ID;
    private boolean isAdmin;
    
    public UserData(String name, String lastName, String userName, boolean isAdmin, String ID) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.ID = ID;
    }

    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }
    public String getID(){return ID;}
    public boolean getIsAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { this.isAdmin = admin; }
}