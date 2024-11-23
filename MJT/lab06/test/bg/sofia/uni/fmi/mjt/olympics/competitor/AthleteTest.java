package bg.sofia.uni.fmi.mjt.olympics.competitor;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AthleteTest {
    private Athlete athlete1;
    private Athlete athlete2;
    private Athlete athlete3;

    @BeforeEach
    public void setUp() {
        athlete1 = new Athlete("001", "Miao Tsa", "Chinese");
        athlete2 = new Athlete("002", "Jose Zuniga", "Mexican");
        athlete3 = new Athlete("002", "Jose Zuniga", "Uruguayan");
    }

    @Test
    @Order(1)
    void testEqualsSameAthletes() {
        Athlete athlete = athlete1;

        assertEquals(athlete1, athlete);
    }

    @Test
    @Order(2)
    void testEqualsDifferentAthletesByName() {
        assertNotEquals(athlete1, athlete2);
    }

    @Test
    @Order(3)
    void testEqualsDifferentAthletesByNationality() {
        assertNotEquals(athlete2, athlete3);
    }

    @Test
    @Order(4)
    void testAddNullMedal() {
        assertThrows(IllegalArgumentException.class, () -> athlete1.addMedal(null));
    }

    @Test
    @Order(5)
    void testAddNotNullMedal() {
        int sizeBefore = athlete1.getMedals().size();

        athlete1.addMedal(Medal.GOLD);

        int sizeAfter = athlete1.getMedals().size();

        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    void testGetMedalsReturnsUnmodifiable() {
        Collection<Medal> medals = athlete3.getMedals();

        assertThrows(UnsupportedOperationException.class, () -> medals.add(Medal.GOLD));
    }
}
