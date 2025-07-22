package view; // O el paquete que estés usando, ej: com.comedor.app

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.User;

/**
 * Un componente visual que muestra el estado del monedero del usuario.
 * Está diseñado para ser integrado en otras vistas, como la barra de navegación.
 */
public class WalletView extends JPanel {
    User user = User.getInstance(); 
    private JLabel balanceLabel; 

    public WalletView() {
        setOpaque(false); // Hacemos el panel base transparente
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Layout para alinear icono y texto
        setBorder(new EmptyBorder(5, 10, 5, 10)); // Margen interno

        // Icono de la billetera (opcional, pero mejora el diseño)
        try {
            Image walletImg = new ImageIcon(getClass().getResource("/assets/wallet.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(walletImg));
            add(iconLabel);
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el icono 'wallet.png'.");
        }

        // Etiqueta para el texto "Saldo:"
        JLabel textLabel = new JLabel("Saldo:");
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        textLabel.setForeground(Color.DARK_GRAY);
        add(textLabel);

        // Etiqueta para el valor del saldo (esta es la que se actualizará)
        balanceLabel = new JLabel(String.format("%.2f", user.getWallet().getBalance())); // Valor inicial
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        balanceLabel.setForeground(Color.DARK_GRAY); // Color por defecto para saldos positivos
        add(balanceLabel);
    }
    public void refreshBalance() {
        balanceLabel.setText(String.format("%.2f", User.getInstance().getWallet().getBalance()));
    }
}