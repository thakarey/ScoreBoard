package main;

import lombok.Data;
import main.cache.Cache;
import main.model.Ball;
import main.model.Innings;
import main.model.Over;
import main.player.Person;
import main.player.Player;
import main.player.PlayerScore;

import java.util.*;
import java.util.stream.Collectors;


//
//There are two innings
// 1st Inning. Team1 Batting
// 2nd Inning. Team2 Bating
/**
 * ScoreBoard is separate for Each innings
 * Contains TotalScore,TotalWickets,TotalExtra runs of that inning.
 * Helps Updating Score for a Ball.
 * Helps Printing the Score At Any given Time
 */

@Data
public class Scorecard {

    private String matchName;
    private int inningsNumber;
    private int totalExtras;
    private int totalScore;
    private int totalWickets;

    private Scorecard(String matchName, int inningsNumber) {
        if (Cache.matchMap.get(matchName) == null) return;
        this.matchName = matchName;
        this.inningsNumber = inningsNumber;
    }

    // Caching the ScoreCard by inningNumber
    public static Scorecard INSTANCE(String matchName, int inningsNumber) {
        Match match = Cache.matchMap.get(matchName);
        if (Cache.scoreCardMap.get(matchName) == null || match.getInnings().get(inningsNumber) == null) {
            Innings innings = match.getInnings().get(inningsNumber);
            if (innings == null) {
                Map<String, PlayerScore> playerScoreMap = null;
                if (inningsNumber == 1) {
                    playerScoreMap = match.getMatchBetween().getTeam1().getPlayers().stream().collect(Collectors.toMap(Person::getName, player -> new PlayerScore(player.getName())));
                } else {
                    playerScoreMap = match.getMatchBetween().getTeam2().getPlayers().stream().collect(Collectors.toMap(Person::getName, player -> new PlayerScore(player.getName())));
                }
                innings = new Innings(inningsNumber);
                match.getInnings().put(inningsNumber, innings);
                match.getInnings().get(inningsNumber).setPlayerScores(playerScoreMap);
            }
            Scorecard scoreCard = new Scorecard(matchName, inningsNumber);
            Map<Integer, Scorecard> scoreCardMap = Cache.scoreCardMap.get(matchName) == null ? new HashMap<>() : Cache.scoreCardMap.get(matchName);
            scoreCardMap.putIfAbsent(inningsNumber, scoreCard);
            Cache.scoreCardMap.put(matchName, scoreCardMap);
        }

        return Cache.scoreCardMap.get(matchName).get(inningsNumber);
    }

    //Called After Each ball
    public void setScoreCardForBall(Ball ball) throws Exception {
        Match match = Cache.matchMap.get(matchName);
        Innings innings = match.getInnings().get(this.inningsNumber);

        // To Add Over if not Present in that inning
        innings.getOvers().putIfAbsent(ball.getOverNumber(), new Over(ball.getOverNumber(), this.inningsNumber));

        switch (ball.getBallType()) {
            case WIDE:
            case NO_BALL:
                updateExtras(ball);
                updateExtraInOver(ball, innings);
                updatePlayerScore(ball, innings);
                break;
            case BALL:
                updateScore(ball, innings);
                //Adding Ball In Current Over
                innings.getOvers().get(ball.getOverNumber()).getBalls().add(ball);
                //Changing the strike of batsman if done with over
                if (innings.getOvers().get(ball.getOverNumber()).getBalls().size() == 6) {
                    Cache.playingPlayers.changeStrike();
                }
                break;
            case WICKET:
                //Adding Ball In Current Over
                innings.getOvers().get(ball.getOverNumber()).getBalls().add(ball);
                updateAfterOut(ball, innings);
                if (inningsNumber == 1) {
                    Player nextPlayer = match.getMatchBetween().getTeam1().getNextPlayer();
                    if (nextPlayer == null) {
                        break;
                    }
                    Cache.playingPlayers.changeAfterWicket(nextPlayer);
                } else {
                    Player nextPlayer = match.getMatchBetween().getTeam2().getNextPlayer();
                    if (nextPlayer == null) {
                        break;
                    }
                    Cache.playingPlayers.changeAfterWicket(nextPlayer);
                    PlayerScore playerScore = innings.getPlayerScores().get(Cache.playingPlayers.getStrikePlayer().getName());
                    playerScore.setOut(false);
                    playerScore.addScore(0);
                }
                if (innings.getOvers().get(ball.getOverNumber()).getBalls().size() == 6) {
                    changeStrike();
                }
                break;
        }

    }

    //Changing the strike of batsman if run = 1,3
    private void updateScore(Ball ball, Innings innings) throws Exception {
        switch (ball.getRunType()) {
            case ONE:
                incrementPlayerScore(ball, innings);
                incrementScore(1);
                changeStrike();
                break;
            case TWO:
                incrementPlayerScore(ball, innings);
                incrementScore(2);
                break;
            case THREE:
                incrementPlayerScore(ball, innings);
                incrementScore(3);
                changeStrike();
                break;
            case FOUR:
                incrementPlayerScore(ball, innings);
                incrementScore(4);
                break;
            case SIX:
                incrementPlayerScore(ball, innings);
                incrementScore(6);
                break;
        }
    }

    private void changeStrike() {
        Cache.playingPlayers.changeStrike();
    }

    private void updatePlayerScore(Ball ball, Innings innings) throws Exception {
        if (Objects.isNull(ball)) throw new Exception("Ball Should contain ball details");
        PlayerScore playerScore = innings.getPlayerScores().get(Cache.playingPlayers.getStrikePlayer().getName());
        playerScore.addScore(0);
    }

    private void incrementPlayerScore(Ball ball, Innings innings) throws Exception {
        if (Objects.isNull(ball)) throw new Exception("Ball Should contain ball details");
        PlayerScore playerScore = innings.getPlayerScores().get(Cache.playingPlayers.getStrikePlayer().getName());
        playerScore.addScore(ball.getRunType().getTotalRuns());
    }

    private void updateAfterOut(Ball ball, Innings innings) throws Exception {
        if (Objects.isNull(ball)) throw new Exception("Ball Should contain ball details");
        PlayerScore playerScore = innings.getPlayerScores().get(Cache.playingPlayers.getStrikePlayer().getName());
        playerScore.setOut(true);
        playerScore.addScore(ball.getRunType().getTotalRuns());
        totalWickets++;
    }

    private void updateExtraInOver(Ball ball, Innings innings) throws Exception {
        if (Objects.isNull(ball)) throw new Exception("Ball Should contain ball details");
        Over over = innings.getOvers().get(ball.getOverNumber());
        over.setExtras(over.getExtras() + ball.getRunType().getTotalRuns());
    }

    private void updateExtras(Ball ball) throws Exception {
        if (Objects.isNull(ball)) throw new Exception("Ball Should contain ball details");
        this.totalExtras += ball.getRunType().getTotalRuns();
        this.totalScore += ball.getRunType().getTotalRuns();
    }

    private void incrementScore(int incrementBy) {
        this.totalScore += incrementBy;
    }

    // Score Details of Current Innings team
    public void printScores() {
        Match match = Cache.matchMap.get(matchName);
        Map<String, PlayerScore> playerScores = match.getInnings().get(inningsNumber).getPlayerScores();
        Innings innings = match.getInnings().get(inningsNumber);
        System.out.println("SCORECARD TEAM " + ((1 == inningsNumber) ? "Team 1" : "Team2"));
        System.out.println("PlayerName      " + "Score      "+ "4s      "+ "6s      "+ "Balls      ");
        for (PlayerScore score : playerScores.values()) {
            System.out.println(score.toString());
        }
        System.out.println();
        System.out.println("TotalScore - " + totalScore + " / " + this.totalWickets);
        System.out.println("Overs - " + getNoOfOversPlusBalls(innings.getOvers()));
    }

    private float getNoOfOversPlusBalls(Map<Integer, Over> overs) {
        List<Integer> lKeys = new ArrayList<>(overs.keySet());
        int integer = lKeys.get(overs.size() - 1);
        int lastOverBalls = overs.get(integer).getBalls().size();
        if (lastOverBalls == 6) {
            return overs.size();
        }
        return (float) (overs.size() - 1) + (lastOverBalls / 10.0f);

    }
}
