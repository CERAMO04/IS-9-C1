// Archivo: src/view/LeftSidePanel.java
package view;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder; 
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LeftSidePanel extends JPanel {
    
    private JButton rechargeButton;
    private User user = User.getInstance(); 

    public LeftSidePanel(boolean showRechargeButton) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ProfilePanel profilePanel = new ProfilePanel();
        CalendarPanel calendarPanel = new CalendarPanel();
        
        profilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, profilePanel.getPreferredSize().height));
        calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendarPanel.getPreferredSize().height));

        add(Box.createVerticalGlue());
        add(profilePanel);
        
        if (showRechargeButton) {
            add(Box.createRigidArea(new Dimension(0, 20)));
            rechargeButton = createStyledButton("Recargar Saldo", new Color(60, 60, 60));
            rechargeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            rechargeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, rechargeButton.getPreferredSize().height));
            add(rechargeButton);
        }
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(calendarPanel);
        add(Box.createVerticalGlue());
    }
    
    public JButton getRechargeButton() {
        return rechargeButton;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20)); 
        button.setOpaque(true); 
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        return button;
    }

    private class ProfilePanel extends RoundedPanel {
        public ProfilePanel() {
            super(30); setBackground(new Color(255, 255, 255, 180)); setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); setBorder(new EmptyBorder(20, 20, 20, 20)); setAlignmentX(Component.CENTER_ALIGNMENT);
            Image userImg = new ImageIcon(getClass().getResource("/assets/user.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel userIconLabel = new JLabel(new ImageIcon(userImg)); userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel name = new JLabel(user.getName()); name.setFont(new Font("SansSerif", Font.BOLD, 16)); name.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel occupation = new JLabel(user.getUserType()); occupation.setForeground(Color.GRAY); occupation.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(userIconLabel); add(Box.createRigidArea(new Dimension(0, 20))); add(name); add(Box.createRigidArea(new Dimension(0, 5))); add(occupation);
        }
    }
    
    private class CalendarPanel extends RoundedPanel {
        public CalendarPanel() {
            super(30);
            setBackground(new Color(255, 255, 255, 180));
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setPreferredSize(new Dimension(280, 280)); 

            // --- Obtener la fecha actual ---
            LocalDate hoy = LocalDate.now();
            int diaActual = hoy.getDayOfMonth();

            // --- Panel del Mes (Título) ---
            // Usamos un formateador para obtener el nombre del mes en español y mayúsculas.
            DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));
            String nombreMes = hoy.format(mesFormatter).toUpperCase();
            
            JLabel monthLabel = new JLabel(nombreMes, SwingConstants.CENTER);
            monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            monthLabel.setForeground(Color.WHITE);
            
            JPanel monthPanel = new JPanel();
            monthPanel.setBackground(new Color(150, 100, 200));
            monthPanel.add(monthLabel);
            add(monthPanel, BorderLayout.NORTH);

            // --- Panel de los Días (Cuadrícula) ---
            JPanel daysPanel = new JPanel(new GridLayout(0, 7, 5, 5));
            daysPanel.setOpaque(false);
            daysPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
            
            // Añadir las cabeceras (L, M, M, J, V, S, D)
            String[] headers = {"LU", "MA", "MI", "JU", "VI", "SA", "DO"}; // Usamos abreviaturas en español
            for (String header : headers) {
                JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
                headerLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                daysPanel.add(headerLabel);
            }

            // --- Lógica para alinear el primer día ---
            LocalDate primerDiaDelMes = hoy.withDayOfMonth(1);
            DayOfWeek diaDeLaSemana = primerDiaDelMes.getDayOfWeek();
            int valorDiaSemana = diaDeLaSemana.getValue(); // Lunes = 1, Martes = 2, ..., Domingo = 7

            // Añadir etiquetas vacías para los días antes del día 1
            for (int i = 1; i < valorDiaSemana; i++) {
                daysPanel.add(new JLabel(""));
            }

            // Añadir los números de los días del mes
            int diasEnMes = hoy.lengthOfMonth();
            for (int i = 1; i <= diasEnMes; i++) {
                JLabel dayLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
                dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

                // --- Resaltar el día actual ---
                if (i == diaActual) {
                    dayLabel.setOpaque(true);
                    dayLabel.setBackground(Color.RED);
                    dayLabel.setForeground(Color.WHITE);
                    dayLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                }
                
                daysPanel.add(dayLabel);
            }
            
            add(daysPanel, BorderLayout.CENTER);
        }
    }
    private class RoundedPanel extends JPanel {
        private final int radius; public RoundedPanel(int radius) { super(); this.radius = radius; setOpaque(false); } @Override protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(getBackground()); g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); g2.dispose(); super.paintComponent(g); }
    }
}