package utils;

import java.awt.image.BufferedImage;

public class ImageUtils {

    public static boolean imagesAreEqual(BufferedImage img1, BufferedImage img2) {
        if (img1 == null || img2 == null) return false;
        
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }
        
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}