package main.model;

import lombok.Data;
import main.Enum.BallType;
import main.Enum.RunType;

/**
 * Contains Meta Data of Ball
 */
@Data
public class Ball {

    private final int ballNumber;
    private final int overNumber;
    private BallType ballType = BallType.BALL;
    private RunType runType = RunType.ZERO;
    private Wicket wicket;

    public Ball(int overNumber, int ballNumber) {
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
    }

    public RunType getRunType() {
        return runType;
    }

    public Object getWicket() {
        return wicket;
    }
}
