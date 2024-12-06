package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LuminosityGrayscaleTest {
    private LuminosityGrayscale luminosityGrayscale;

    @BeforeEach
    void setUp() {
        luminosityGrayscale = new LuminosityGrayscale();
    }

    @Test
    void testNullImage() {
        assertThrows(
            IllegalArgumentException.class,
            () -> luminosityGrayscale.process(null)
        );
    }

    @Test
    void testProcess() {

    }
}
