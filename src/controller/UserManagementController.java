package controller;


import view.UserManagementView;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import controller.MainController;
import model.UserData;
import model.User;
import model.persistence.UserFile;


public class UserManagementController {
    private MainController mainController;
    private UserManagementView view;
    private List<UserData> users;
    UserFile userFile = new UserFile();

    public UserManagementController(UserManagementView view, MainController mainController){
        this.mainController = mainController;
        this.view = view;

        loadUsers();
        
        JList<String> userList = view.getUserList();


        view.getRevokeButton().addActionListener(e -> {
            int selectedIndex = userList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(view, "Por favor selecciona un usuario");
                return;
            }
            UserData selectedUser = users.get(selectedIndex);
            try {
                if (userFile.userExists(selectedUser.getID())){
                    userFile.downPrivileg(selectedUser.getID());
                }
                JOptionPane.showMessageDialog(view, selectedUser.getName() + " ya no es administrador");
                loadUsers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error revocando permisos: " + ex.getMessage());
            }
        });

        view.getPromoteButton().addActionListener(e -> {
            int selectedIndex = userList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(view, "Por favor selecciona un usuario");
                return;
            }
            UserData selectedUser = users.get(selectedIndex);
            try {
                if (userFile.userExists(selectedUser.getID())){
                    userFile.upPrivileg(selectedUser.getID());
                }
                JOptionPane.showMessageDialog(view, selectedUser.getName() + " ahora es administrador");
                loadUsers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error al promover al usuario: " + ex.getMessage());
            }
        });
    }

    private void loadUsers() {
        try {
            users = userFile.readAllUsers();

            view.setUsers(users); 

            DefaultListModel<String> listModel = view.getListModel();
            listModel.clear();

            for (UserData user : users) {
                String adminStatus = user.getIsAdmin() ? " (Es administrador)" : " (No es administrador)";
                listModel.addElement(user.getName() + " " + user.getLastName() + " (" + user.getID() + ")" + adminStatus);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error cargando usuarios: " + e.getMessage());
        }
    }

}
