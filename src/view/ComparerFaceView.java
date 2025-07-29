package view;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ComparerFaceView extends JFrame {

    private JButton loadImageButton;
    private JLabel imageLabel;
    private JFileChooser fileChooser;

    public ComparerFaceView() {
        setTitle("Comparador de Imágenes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); 

        JPanel contentPane = new JPanel(new GridBagLayout()) {
            Image background = new ImageIcon(getClass().getResource("/assets/comedor.jpeg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("Imagen de fondo no encontrada: /assets/comedor.jpeg", 20, 20);
                }
            }
        };
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        imageLabel = new JLabel("No hay imagen cargada", SwingConstants.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 400));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(255, 255, 255, 180));
        gbc.gridy = 0;
        contentPane.add(imageLabel, gbc);

        loadImageButton = new JButton("Cargar Imagen");
        gbc.gridy = 1;
        contentPane.add(loadImageButton, gbc);

        fileChooser = new JFileChooser();

        loadImageButton.addActionListener(e -> loadImage());
    }

    public JButton getLoadImageButton() {
        return loadImageButton;
    }

    public void displayImage(File imageFile) {
        try {
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.");
                return;
            }

            int labelWidth = imageLabel.getWidth();
            int labelHeight = imageLabel.getHeight();

            if (labelWidth == 0 || labelHeight == 0) {
                labelWidth = 400;
                labelHeight = 400;
            }

            Image scaledImage = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
            imageLabel.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
        }
    }

    private void loadImage() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayImage(selectedFile);
        }
    }

}
