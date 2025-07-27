package controller;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
            }
        });



        
    }


}
