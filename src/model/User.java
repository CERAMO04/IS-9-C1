package model;

public class User {
    /*Atrib */
    private String name;
    private String lastName;
    private String user;
    private String password;
    private String email;
    private String userType;
    private boolean isAdmin;
    /*Builders. */
    public User(String name, String lastName, String user, String password, String email, 
                String userType, boolean isAdmin){
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
    public String getName() {return this.name;}
    public String getLastName() {return this.lastName;}
    public String getPassword() {return password;}
    public String getEmail() {return password;}
    public String getUserType() {return userType;}
    public boolean getIsAdmin() {return isAdmin;}
    /*Functions */

}