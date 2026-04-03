package com.dimitris.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam, "Home team must not be null or blank");
        validateTeamName(awayTeam, "Away team must not be null or blank");

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home team and away team must be different");
        }

        boolean alreadyExists = matches.stream()
            .anyMatch(match ->
                    match.getHomeTeam().equals(homeTeam) &&
                    match.getAwayTeam().equals(awayTeam));

        if (alreadyExists) {
            throw new IllegalStateException("Match already exists");
        }

        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateScore(homeScore);
        validateScore(awayScore);

        Match match = matches.stream()
                .filter(existingMatch ->
                        existingMatch.getHomeTeam().equals(homeTeam) &&
                        existingMatch.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Match does not exist"));

        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        boolean removed = matches.removeIf(match ->
                match.getHomeTeam().equals(homeTeam) &&
                match.getAwayTeam().equals(awayTeam));

        if (!removed) {
            throw new IllegalStateException("Match does not exist");
        }
    }

    public List<Match> getSummary() {
        return List.copyOf(matches);
    }

    private void validateTeamName(String teamName, String errorMessage) {
        if (teamName == null || teamName.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void validateScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Scores must not be negative");
        }
    }
}