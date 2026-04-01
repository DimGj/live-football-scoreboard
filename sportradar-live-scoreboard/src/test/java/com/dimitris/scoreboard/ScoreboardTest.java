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
}
