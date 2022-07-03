package main;

import main.cache.Cache;
import main.model.Ball;
import main.model.Innings;

/**
 * 1. This is Score Updater Class
 * 2. Announce Winner
 */
public class Scorer {

    public void setScore(Ball ball, String match, int innings) {
        Scorecard scoreCard = Scorecard.INSTANCE(match, innings);
        try {
            scoreCard.setScoreCardForBall(ball);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printScore(String match, int innings) {
        Scorecard scoreCard = Scorecard.INSTANCE(match, innings);
        scoreCard.printScores();
    }

    public void announceWinner(String matchName) {
        Scorecard inningOne = Scorecard.INSTANCE(matchName, 1);
        Scorecard inningTwo = Scorecard.INSTANCE(matchName, 2);
        int teamOneScore = inningOne.getTotalScore();
        int teamTwoScore = inningTwo.getTotalScore();

        int playerCount = getPlayerCount(2, matchName);

        int teamTwoWickets = inningTwo.getTotalWickets();

        if (teamOneScore > teamTwoScore) {
            System.out.println(matchName + " : Team 1 won the matchName by " + (teamOneScore - teamTwoScore) + " Runs");
        } else if (teamOneScore < teamTwoScore) {
            System.out.println(matchName + " : Team 2 won the matchName by " + (playerCount - teamTwoWickets) + " Wickets");
        } else {
            System.out.println(matchName + " is Draw");
        }
    }

    private int getPlayerCount(int inningCount, String matchName) {
        Match match = Cache.matchMap.get(matchName);
        Innings innings = match.getInnings().get(inningCount);
        return innings.getPlayerScores().size();
    }
}

