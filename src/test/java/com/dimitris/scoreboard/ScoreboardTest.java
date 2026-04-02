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
}
