package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private static final int[][] VERTICAL_SOBEL_KERNEL = {
        {-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}
    };

    private static final int[][] HORIZONTAL_SOBEL_KERNEL = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };

    private static final int MAX_PIXEL_VALUE = 255;

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        BufferedImage grayImage = grayscaleAlgorithm.process(image);
        int imageWidth = grayImage.getWidth();
        int imageHeight = grayImage.getHeight();
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 1; x < imageHeight - 1; x++) {
            for (int y = 1; y < imageWidth - 1; y++) {
                int verticalResult = 0;
                int horizontalResult = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int pixel = (new Color(grayImage.getRGB(y + l, x + k)).getRed());
                        verticalResult += pixel * VERTICAL_SOBEL_KERNEL[k + 1][l + 1];
                        horizontalResult += pixel * HORIZONTAL_SOBEL_KERNEL[k + 1][l + 1];
                    }
                }

                int newGray = (int) Math.min(
                    Math.sqrt((verticalResult * verticalResult) + (horizontalResult * horizontalResult)),
                    MAX_PIXEL_VALUE
                );
                resultImage.setRGB(y, x, new Color(newGray, newGray, newGray).getRGB());
            }
        }
        return resultImage;
    }
}