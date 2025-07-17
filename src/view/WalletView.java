package view; // O el paquete que estés usando, ej: com.comedor.app

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Un componente visual que muestra el estado del monedero del usuario.
 * Está diseñado para ser integrado en otras vistas, como la barra de navegación.
 */
public class WalletView extends JPanel {

    private JLabel balanceLabel; // Etiqueta que muestra el monto del saldo

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
        balanceLabel = new JLabel("0.00 Bs."); // Valor inicial
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        balanceLabel.setForeground(Color.DARK_GRAY); // Color por defecto para saldos positivos
        add(balanceLabel);
    }
    
    /**
     * Método público para que el controlador actualice el saldo mostrado en la vista.
     * @param newBalance El nuevo saldo a mostrar.
     */
    public void updateBalance(double newBalance) {
        // Formateamos el número a dos decimales y añadimos la moneda
        String formattedBalance = String.format("%.2f Bs.", newBalance);
        balanceLabel.setText(formattedBalance);

        // Lógica para cambiar el color si el saldo es negativo
        if (newBalance < 0) {
            balanceLabel.setForeground(Color.RED);
        } else {
            // Si no es negativo, usamos el color estándar (verde o gris oscuro)
            balanceLabel.setForeground(new Color(0, 100, 0)); // Un verde oscuro se ve bien
        }
    }
}