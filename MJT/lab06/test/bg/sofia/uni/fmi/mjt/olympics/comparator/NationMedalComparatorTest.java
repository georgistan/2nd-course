package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NationMedalComparatorTest {
    @Mock
    private MJTOlympics olympics;

    @InjectMocks
    private NationMedalComparator comparator;

    private String nation1;
    private String nation2;
    private String nation3;

    @BeforeEach
    public void setUp() {
        nation1 = "Bulgarian";
        nation2 = "Japan";
        nation3 = "Nigerian";

        when(olympics.getTotalMedals(nation1)).thenReturn(100);
    }

    @Test
    void testCompareSameMedals() {
        when(olympics.getTotalMedals(nation3)).thenReturn(100);

        assertEquals(nation1.compareTo(nation3), comparator.compare(nation1, nation3));
    }

    @Test
    void testCompareNationsFirstBigger() {
        when(olympics.getTotalMedals(nation2)).thenReturn(200);

        assertEquals(1, comparator.compare(nation2, nation1));
    }

    @Test
    void testCompareNationsSecondBigger() {
        when(olympics.getTotalMedals(nation2)).thenReturn(200);

        assertEquals(-1, comparator.compare(nation1, nation2));
    }
}