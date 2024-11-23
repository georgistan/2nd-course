package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MJTOlympicsTest {
    @Mock
    private CompetitionResultFetcher competitionResultFetcher;

    private Set<Competitor> competitors;

    @Mock
    private Map<String, EnumMap<Medal, Integer>> nationsMedalTable;

    private Olympics olympics;

    @BeforeEach
    void setUp() {
        competitors = new HashSet<>();
        competitors.add(new Athlete("001", "Miao Tsa", "Chinese"));
        competitors.add(new Athlete("002", "Jose Zuniga", "Mexican"));
        competitors.add(new Athlete("002", "Jose Zuniga", "Uruguayan"));

        olympics = new MJTOlympics(competitors, competitionResultFetcher);
    }

    @Test
    void testValidateNullNationality() {
        assertThrows(IllegalArgumentException.class, () -> olympics.getTotalMedals(null));
    }

    @Test
    void testValidateNotRegisteredNationality() {
        assertThrows(IllegalArgumentException.class, () -> olympics.getTotalMedals("invalid_nationality_that_throws"));
    }

    @Test
    void testValidateNullCompetition() {
        assertThrows(IllegalArgumentException.class, () -> olympics.updateMedalStatistics(null));
    }

    @Test
    void testValidateNotRegisteredCompetitor() {
        Competition competition = new Competition(
            "TUES Gaming Tournament",
            "gaming",
            new HashSet<>()
            );

        assertThrows(IllegalArgumentException.class, () -> olympics.updateMedalStatistics(competition));
    }
}

/*
Compilation successful!

Your tests result: 13 found, 12 passed, 1 failed, 0 aborted, 0 skipped
Line code coverage from your unit tests: 60.98% (50/82), which is a rather OK result.

Reference tests result: 28 found, 14 passed, 14 failed, 0 aborted, 0 skipped
Here are some hints to help you improve your solution:

[Hint] Nations should be sorted by total medal counts. ==> expected: <[NationB, NationA]> but was: <[NationA, NationB]>
[Hint] Competitors cannot be null or empty ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] Discipline cannot be null or blank ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] Check the logic for sorting nations by medal count and breaking ties alphabetically. ==> expected: <[NationA, NationB]> but was: <[NationB, NationA]>
[Hint] The total number of medals per nation should be properly counted ==> expected: <1> but was: <-1>
[Hint] Competition's name cannot be null or blank ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] Ensure the correct nation receives its medal. ==> expected: <2> but was: <0>
[Hint] Competitors cannot be null or empty ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] Discipline cannot be null or blank ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] The number of an athlete's medals of a particular type should be properly counted ==> expected: <3> but was: <1>
[Hint] Nations should be sorted by total medal counts. ==> expected: <[NationB, NationA, NationC]> but was: <[NationA, NationC, NationB]>
[Hint] Verify that you correctly sum all medals for the nation. ==> expected: <3> but was: <2>
[Hint] Competition's name cannot be null or blank ==> Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[Hint] Ensure the top competitor receives the GOLD medal. ==> expected: <1> but was: <-1>

Kudos! Checkstyle audited your submission and says you are a Clean Coder.
 */