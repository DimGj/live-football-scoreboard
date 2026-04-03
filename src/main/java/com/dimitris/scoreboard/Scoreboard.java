package com.dimitris.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();
    private long nextStartOrder = 0;

    public void startMatch(String homeTeam, String awayTeam) {
        String normalizedHomeTeam = normalizeTeamName(homeTeam, "Home team must not be null or blank");
        String normalizedAwayTeam = normalizeTeamName(awayTeam, "Away team must not be null or blank");

        if (normalizedHomeTeam.equals(normalizedAwayTeam)) {
            throw new IllegalArgumentException("Home team and away team must be different");
        }

        boolean alreadyExists = matches.stream()
            .anyMatch(match ->
                    match.getHomeTeam().equals(normalizedHomeTeam) &&
                    match.getAwayTeam().equals(normalizedAwayTeam));

        if (alreadyExists) {
            throw new IllegalStateException("Match already exists");
        }

        matches.add(new Match(normalizedHomeTeam, normalizedAwayTeam, ++nextStartOrder));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateScore(homeScore);
        validateScore(awayScore);

        String normalizedHomeTeam = normalizeTeamName(homeTeam, "Home team must not be null or blank");
        String normalizedAwayTeam = normalizeTeamName(awayTeam, "Away team must not be null or blank");

        Match match = matches.stream()
                .filter(existingMatch ->
                        existingMatch.getHomeTeam().equals(normalizedHomeTeam) &&
                        existingMatch.getAwayTeam().equals(normalizedAwayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Match does not exist"));

        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        String normalizedHomeTeam = normalizeTeamName(homeTeam, "Home team must not be null or blank");
        String normalizedAwayTeam = normalizeTeamName(awayTeam, "Away team must not be null or blank");

        boolean removed = matches.removeIf(match ->
                match.getHomeTeam().equals(normalizedHomeTeam) &&
                match.getAwayTeam().equals(normalizedAwayTeam));

        if (!removed) {
            throw new IllegalStateException("Match does not exist");
        }
    }

    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt((Match m) -> m.getHomeScore() + m.getAwayScore())
                        .reversed()
                        .thenComparing(Comparator.comparingLong(Match::getStartOrder).reversed()))
                .toList();
    }

    private String normalizeTeamName(String teamName, String errorMessage) {
        if (teamName == null) {
            throw new IllegalArgumentException(errorMessage);
        }

        String normalizedTeamName = teamName.trim();

        if (normalizedTeamName.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return normalizedTeamName;
    }

    private void validateScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Scores must not be negative");
        }
    }
}