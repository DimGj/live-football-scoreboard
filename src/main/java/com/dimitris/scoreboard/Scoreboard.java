package com.dimitris.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam, "Home team must not be null or blank");
        validateTeamName(awayTeam, "Away team must not be null or blank");
        matches.add(new Match(homeTeam, awayTeam));
    }

    public List<Match> getSummary() {
        return List.copyOf(matches);
    }

    private void validateTeamName(String teamName, String errorMessage) {
        if (teamName == null || teamName.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}