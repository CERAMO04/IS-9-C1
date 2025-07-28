package controller;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;

import model.User;
import model.persistence.UserFile;
import view.ComparerFaceView;


public class ComparerFaceController {
    
    private ComparerFaceView view;
    private MainController mainController;

    public ComparerFaceController(ComparerFaceView view, MainController mainController){
        this.view = view;
        this.mainController = mainController;

        view.getLoadImageButton().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Imágenes (.jpg, .jpeg, .png)", "jpg", "jpeg", "png"
            );
            fileChooser.setFileFilter(imageFilter);

            int option = fileChooser.showOpenDialog(view);
            
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                view.displayImage(selectedFile);
                try {
                    BufferedImage img = ImageIO.read(selectedFile);
                    if (img == null) {
                        JOptionPane.showMessageDialog(view, "Imagen invalida");   
                        return;
                    }
                    UserFile userFile = new UserFile();
                    boolean found = userFile.isImageInUserBase(img);
                    if (found) {
                        JOptionPane.showMessageDialog(view, "Buen provecho!");  
                        

                    } else {
                        JOptionPane.showMessageDialog(view, "Usuario no encontrado");  
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
