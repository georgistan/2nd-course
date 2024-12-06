package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    private static final float RED_COEFFICIENT = 0.21f;
    private static final float GREEN_COEFFICIENT = 0.72f;
    private static final float BLUE_COEFFICIENT = 0.07f;

    public LuminosityGrayscale() { }

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                Color color = new Color(image.getRGB(y, x));

                int newGray = (int) (
                    color.getRed() * RED_COEFFICIENT +
                    color.getGreen() * GREEN_COEFFICIENT +
                    color.getBlue() * BLUE_COEFFICIENT
                );

                Color newColor = new Color(newGray, newGray, newGray);

                image.setRGB(y, x, newColor.getRGB());
            }
        }

        return image;
    }
}
