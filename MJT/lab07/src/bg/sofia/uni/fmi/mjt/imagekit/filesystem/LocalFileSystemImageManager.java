package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    public LocalFileSystemImageManager() { }

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }

        if (!imageFile.exists()) {
            throw new IOException("Image file " + imageFile + " does not exist");
        }

        if (!imageFile.isFile()) {
            throw new IOException("Image file " + imageFile + " is not a regular file");
        }

        if (isImageOfSupportedFormat(imageFile)) {
            return ImageIO.read(imageFile);
        } else {
            throw new IOException("Image file " + imageFile + " is not of a supported format");
        }
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }

        if (!imagesDirectory.exists()) {
            throw new IOException("Image directory " + imagesDirectory + " does not exist");
        }

        if (!imagesDirectory.isDirectory()) {
            throw new IOException("Image directory " + imagesDirectory + " is not a directory");
        }

        List<BufferedImage> images = new ArrayList<>();

        for (File imageFile : imagesDirectory.listFiles()) {
            if (imageFile != null && imageFile.isFile()) {
                images.add(loadImage(imageFile));
            }
        }

        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }

        if (imageFile.exists()) {
            throw new IOException("Image file " + imageFile + " already exists");
        }

        if (imageFile.getParent() == null) {
            throw new IOException("Image parent directory does not exist");
        }

        if (isImageOfSupportedFormat(imageFile)) {
            ImageIO.write(image, "png", imageFile);
        } else {
            throw new IOException("Image file " + imageFile + " is not of a supported format");
        }
    }

    private boolean isImageOfSupportedFormat(File imageFile) {
        return imageFile.getName().endsWith(".png") ||
            imageFile.getName().endsWith(".jpg") ||
            imageFile.getName().endsWith(".jpeg");
    }
}