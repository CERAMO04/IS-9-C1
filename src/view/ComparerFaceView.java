package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ComparerFaceView extends JFrame {

    private JButton loadImageButton;
    private JLabel imageLabel;

    public ComparerFaceView() {
        setTitle("Comparador de Imágenes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Botón para cargar imagen
        loadImageButton = new JButton("Cargar Imagen");
        add(loadImageButton, BorderLayout.NORTH);

        // Label para mostrar imagen
        imageLabel = new JLabel("No hay imagen cargada", SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);
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

            ImageIcon icon = new ImageIcon(img.getScaledInstance(400, 400, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
            imageLabel.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
        }
    }


}
