package main.model;

import lombok.Data;
import main.Enum.RunType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains - List of Balls
 *             TotalRunInOver
 *             getDotBallsInOver()
 *             getWicketsInOver()
 *             isMaidenOver() // check for maidenOver
 */
@Data
public class Over {
    private int innings;
    private int number;
    private int runsScored = 0;
    private int extras = 0;
    private int totalRunsInOver;

    private List<Ball> balls;

    public Over(int number, int innings) {
        this.number = number;
        this.innings = innings;
        balls = new ArrayList<>();
    }


    public int getTotalRunsInOver() {
        return runsScored + extras;
    }

    private int dotBollsInOver(){
        int dotBalls = 0;
        for (Ball ball : balls){
            if(RunType.ZERO.equals(ball.getRunType())){
                dotBalls++;
            }
        }
        return dotBalls;
    }
    private boolean isMaidenOver() {
        final Optional<Ball> any = balls.stream().filter(ball -> !RunType.ZERO.equals(ball.getRunType())).findAny();
        return any.isPresent();
    }

    private int getWicketsInOver() {
        int wkCount = 0;
        for (Ball ball : balls) {
            if (ball.getWicket() != null) {
                wkCount += 1;
            }
        }
        return wkCount;
    }



}