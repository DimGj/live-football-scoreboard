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
    void shouldTrimTeamNamesWhenStartingMatch() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("  Brazil  ", "  Spain  ");

        Match match = scoreboard.getSummary().get(0);

        assertEquals("Brazil", match.getHomeTeam());
        assertEquals("Spain", match.getAwayTeam());
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

    @Test
    void shouldRejectFinishForNonExistingMatch() {
        Scoreboard scoreboard = new Scoreboard();

        IllegalStateException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalStateException.class,
            () -> scoreboard.finishMatch("France", "Russia")
        );

        assertEquals("Match does not exist", exception.getMessage());
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatches() {
        Scoreboard scoreboard = new Scoreboard();

        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void shouldReturnMatchesOrderedByTotalScoreDescending() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Mexico", "USA");
        scoreboard.updateScore("Mexico", "USA", 5, 1); // sum: 6

        scoreboard.startMatch("Italy", "Brazil");
        scoreboard.updateScore("Italy", "Brazil", 8, 2); // sum: 10

        scoreboard.startMatch("Russia", "France");
        scoreboard.updateScore("Russia", "France", 1, 3); // sum: 4

        var summary = scoreboard.getSummary();
        assertEquals("Italy", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
        assertEquals("Russia", summary.get(2).getHomeTeam());
    }

    @Test
    void shouldReturnTiedMatchesByMostRecentlyStartedFirst() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("USA", "Canada");
        scoreboard.updateScore("USA", "Canada", 3, 3); // sum: 6

        scoreboard.startMatch("Chile", "Brazil");
        scoreboard.updateScore("Chile", "Brazil", 3, 3); // sum: 6

        var summary = scoreboard.getSummary();
        assertEquals("Chile", summary.get(0).getHomeTeam());
        assertEquals("USA", summary.get(1).getHomeTeam());
    }

    @Test
    void shouldMatchExerciseExampleSummary() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5); // sum: 5

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2); // sum: 12

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2); // sum: 4

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6); // sum: 12

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1); // sum: 4

        var summary = scoreboard.getSummary();
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }
}
