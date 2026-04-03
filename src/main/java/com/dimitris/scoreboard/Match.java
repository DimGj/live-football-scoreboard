package com.dimitris.scoreboard;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private final long startOrder;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam, long startOrder) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startOrder = startOrder;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public long getStartOrder() {
        return startOrder;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void updateScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}