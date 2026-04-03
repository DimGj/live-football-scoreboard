package com.dimitris.scoreboard;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreboardTest {

    @Test
    void shouldStartNewMatchWithInitialScoreZeroToZero() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Brazil", "Spain");

        List<Match> summary = scoreboard.getSummary();

        assertEquals(1, summary.size());

        Match match = summary.get(0);
        assertEquals("Brazil", match.getHomeTeam());
        assertEquals("Spain", match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldRejectNullHomeTeam() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.startMatch(null, "Norway")
        );

        assertEquals("Home team must not be null or blank", exception.getMessage());
    }

    @Test
    void shouldRejectNullAwayTeam() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.startMatch("Brazil", null)
        );

        assertEquals("Away team must not be null or blank", exception.getMessage());
    }

    @Test
    void shouldRejectBlankHomeTeam() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.startMatch("   ", "Norway")
        );

        assertEquals("Home team must not be null or blank", exception.getMessage());
    }

    @Test
    void shouldRejectBlankAwayTeam() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.startMatch("Brazil", "   ")
        );

        assertEquals("Away team must not be null or blank", exception.getMessage());
    }

    @Test
    void shouldRejectSameHomeAndAwayTeam() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.startMatch("Norway", "Norway")
        );

        assertEquals("Home team and away team must be different", exception.getMessage());
    }

    @Test
    void shouldRejectDuplicateMatch() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Brazil", "Spain");

        IllegalStateException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalStateException.class,
            () -> scoreboard.startMatch("Brazil", "Spain")
        );

        assertEquals("Match already exists", exception.getMessage());
    }

    @Test
    void shouldUpdateScoreForExistingMatch() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Norway", "Greece");
        scoreboard.updateScore("Norway", "Greece", 3, 1);

        Match match = scoreboard.getSummary().get(0);

        assertEquals(3, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }

    @Test
    void shouldRejectNegativeHomeScore() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Brazil", "Spain");

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.updateScore("Brazil", "Spain", -1, 2)
        );

        assertEquals("Scores must not be negative", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeAwayScore() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Brazil", "Spain");

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> scoreboard.updateScore("Brazil", "Spain", 2, -2)
        );

        assertEquals("Scores must not be negative", exception.getMessage());
    }

    @Test
    void shouldRejectUpdateForNonExistingMatch() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalStateException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalStateException.class,
            () -> scoreboard.updateScore("Zambia", "Italy", 5, 5)
        );

        assertEquals("Match does not exist", exception.getMessage());
    }

    @Test
    void shouldFinishExistingMatch() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("France", "Russia");
        scoreboard.finishMatch("France", "Russia");

        assertEquals(0, scoreboard.getSummary().size());
    }
}
