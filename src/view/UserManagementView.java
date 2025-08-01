package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import model.UserData;

public class UserManagementView extends JFrame {
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private static boolean isOpen = false;
    private JButton promoteButton, revokeButton;
    private List<UserData> users;

    public UserManagementView() {
        if (isOpen) return; // Evita múltiples ventanas
        isOpen = true;

        setTitle("Manejo de Usuarios");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                isOpen = false;
            }
        });

        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        promoteButton = new JButton("Promover a admin");
        revokeButton = new JButton("Revocar admin");

        promoteButton.setEnabled(false);
        revokeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(promoteButton);
        buttonPanel.add(revokeButton);

        userList.addListSelectionListener(e -> {
            boolean hasSelection = !userList.isSelectionEmpty();
            if (hasSelection && users != null && userList.getSelectedIndex() < users.size()) {
                UserData selectedUser = users.get(userList.getSelectedIndex());
                promoteButton.setEnabled(!selectedUser.getIsAdmin());
                revokeButton.setEnabled(selectedUser.getIsAdmin());
            } else {
                promoteButton.setEnabled(false);
                revokeButton.setEnabled(false);
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Setters
    public void setUsers(List<UserData> users) {
        this.users = users;
    }

    // Getters
    public JButton getPromoteButton() { return promoteButton; }
    public JButton getRevokeButton() { return revokeButton; }
    public JList<String> getUserList() { return userList; }
    public DefaultListModel<String> getListModel() { return listModel; }
}
