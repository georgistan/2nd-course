package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SobelEdgeDetectionTest {
    private ImageAlgorithm grayscaleAlgorithm;
    private SobelEdgeDetection sobelEdgeDetection;

    @BeforeEach
    void setUp() {
        grayscaleAlgorithm = new LuminosityGrayscale();
        sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
    }

    @Test
    void testNullImage() {
        assertThrows(IllegalArgumentException.class, () -> sobelEdgeDetection.process(null));
    }
}
