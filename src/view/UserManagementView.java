package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;
import model.User;
import model.persistence.UserService;

public class UserManagementView extends JFrame {
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private List<User> users;
    private static boolean isOpen = false;

    public UserManagementView() {

        if (isOpen) {
            return; // Evita múltiples instancias
        }
        isOpen = true;

        setTitle("Manejo de Usuarios");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                isOpen = false;
            }
        });
        
        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton promoteButton = new JButton("Promover a admin");
        promoteButton.addActionListener(e -> promoteSelectedUser());

        JButton revokeButton = new JButton("Revocar admin");
        revokeButton.addActionListener(e -> revokeSelectedUser());
        revokeButton.setEnabled(false);
        
        buttonPanel.add(promoteButton);
        buttonPanel.add(revokeButton);
        
        userList.addListSelectionListener(e -> {
            boolean hasSelection = !userList.isSelectionEmpty();
            promoteButton.setEnabled(hasSelection && 
                !users.get(userList.getSelectedIndex()).getIsAdmin());
            revokeButton.setEnabled(hasSelection && 
                users.get(userList.getSelectedIndex()).getIsAdmin());
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
        loadUsers();
    }

    private void revokeSelectedUser() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario");
            return;
        }
        
        User selectedUser = users.get(selectedIndex);
        try {
            UserService.revokeAdmin(selectedUser.getID());
            JOptionPane.showMessageDialog(this, 
                selectedUser.getName() + " ya no es administrador");
            loadUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error revocando permisos: " + e.getMessage());
        }
    }

    private void loadUsers() {
        try {
            users = UserService.getAllUsers();
            listModel.clear();
            for (User user : users) {
                String adminStatus = user.getIsAdmin() ? " (Es administrador)" : " (No es administrador)";
                listModel.addElement(user.getName() + " " + user.getLastName() + " (" + user.getID() + ")" + adminStatus);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void promoteSelectedUser() {
        int selectedIndex = userList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario");
            return;
        }
        
        User selectedUser = users.get(selectedIndex);
        try {
            UserService.promoteToAdmin(selectedUser.getID());
            JOptionPane.showMessageDialog(this, 
                selectedUser.getName() + " ahora es administrador");
            loadUsers(); // Refresca la lista
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al promover al usuario: " + e.getMessage());
        }
    }
}