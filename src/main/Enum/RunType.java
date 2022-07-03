package main.Enum;

public enum RunType {
    ZERO(0),

    //not Adding extraruns for wide (1,0) (1,1) (1,4)
    WIDE(1),
    WIDE_FOUR(1,4),

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    SIX(6),

    //(1,0) NO BALL with extra 0 runs  on that ball
    //(1,4) NO BALL with with extra 4 runs on that ball
    NO_BALL_ZERO(1,0),
    NO_BALL_ONE(1,1),
    NO_BALL_TWO(1,2),
    NO_BALL_THREE(1,3),
    NO_BALL_FOUR(1,4),
    NO_BALL_SIX(1,6);


    int run;
    int extraRun;

    public int getTotalRuns() {
        return run + extraRun;
    }
    RunType(int i) {
        this.run = i;
    }

    RunType(int i, int j) {
        this.run = i;
        this.extraRun = j;
    }
}
