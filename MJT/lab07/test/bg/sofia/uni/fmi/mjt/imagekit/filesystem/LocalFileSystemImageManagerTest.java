package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocalFileSystemImageManagerTest {
    private LocalFileSystemImageManager imageManager;

    @BeforeEach
    public void setUp() {
        imageManager = new LocalFileSystemImageManager();
    }

    @AfterEach
    public void tearDown() {
        imageManager = null;
    }

    @Test
    @Order(1)
    void testLoadNullImageFile() {
        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImage(null));
    }

    @Test
    @Order(2)
    void testLoadNonExistentImageFile() {
        assertThrows(IOException.class, () -> imageManager.loadImage(new File("")));
    }

    @Test
    @Order(3)
    void testLoadFileThatIsNotFile() {
        File imageFile = mock(File.class);

        when(imageFile.isFile()).thenReturn(false);

        assertThrows(IOException.class, () -> imageManager.loadImage(imageFile));
    }

    @Test
    @Order(4)
    void testLoadFileOfUnsupportedFormat() {
        File imageFile = new File("tree.dat");

        assertThrows(IOException.class, () -> imageManager.loadImage(imageFile));
    }

    @Test
    @Order(5)
    void testLoadImage() {
        File imageFile = new File("kitten.png");

        assertDoesNotThrow(() -> imageManager.loadImage(imageFile));
    }

    @Test
    @Order(6)
    void testLoadImagesNullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImagesFromDirectory(null));
    }

    @Test
    @Order(7)
    void testLoadImagesFromDirectoryForNonExistentDirectory() {
        File imageDirectory = mock(File.class);

        when(imageDirectory.exists()).thenReturn(false);

        assertThrows(IOException.class, () -> imageManager.loadImagesFromDirectory(imageDirectory));
    }

    @Test
    @Order(8)
    void testLoadImagesFromDirectoryThatIsNotDirectory() {
        File imageDirectory = mock(File.class);

        when(imageDirectory.isDirectory()).thenReturn(false);

        assertThrows(IOException.class, () -> imageManager.loadImagesFromDirectory(imageDirectory));
    }

    @Test
    @Order(9)
    void testSaveNullImage() {
        assertThrows(
            IllegalArgumentException.class,
            () -> imageManager.saveImage(null, new File("myFiles"))
        );
    }

    @Test
    @Order(10)
    void testSaveNullImageFile() {
        BufferedImage image = mock(BufferedImage.class);

        assertThrows(IllegalArgumentException.class, () -> imageManager.saveImage(image, null));
    }

    @Test
    @Order(11)
    void testSaveExistingImageFile() {
        BufferedImage image = mock(BufferedImage.class);
        File imageFile = mock(File.class);

        when(imageFile.exists()).thenReturn(true);

        assertThrows(IOException.class, () -> imageManager.saveImage(image, imageFile));
    }

    @Test
    @Order(12)
    void testSaveImageFileNullParent() {
        BufferedImage image = mock(BufferedImage.class);
        File imageFile = mock(File.class);

        when(imageFile.getParent()).thenReturn(null);

        assertThrows(IOException.class, () -> imageManager.saveImage(image, imageFile));
    }

    @Test
    @Order(13)
    void testSaveImageFileOfUnsupportedFormat() {
        BufferedImage image = mock(BufferedImage.class);
        File imageFile = new File("car.txt");

        assertThrows(IOException.class, () -> imageManager.saveImage(image, imageFile));
    }

//    @Test
//    @Order(14)
//    void testSaveImage() {
//        BufferedImage image = mock(BufferedImage.class);
//        File imageFile = new File("cooler-car.jpg");
//
//        when(imageFile.getParent()).thenReturn("C:\\Joro\\MJT\\lab07");
//
//        assertDoesNotThrow(() -> imageManager.saveImage(image, imageFile));
//        assertTrue(imageFile.exists());
//    }
}
