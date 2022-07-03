package main.player;

import lombok.Getter;
import lombok.Setter;
import main.Enum.WicketType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerScore {
    private final String name;
    private List<Integer> runsScored;
    private WicketType wicketType;
    private String bowler;
    private boolean isOut;
    private int totalBoundaries;
    private int totalSixes;
    private boolean isPlayed;

    public PlayerScore(String name) {
        this.name = name;
        runsScored = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getName() + "          "+(isPlayed && !isOut ? "*" : " ") +"     "+ getTotalRunsScore() +"           "+ totalBoundaries + "      " + totalSixes + "       " + runsScored.size();
    }

    public int getTotalRunsScore() {
        int sum = 0;
        for (Integer i : runsScored) {
            sum += i;
        }
        return sum;
    }

    public void addScore(int n) throws Exception {
        if (n < 0 || n > 6) {
            throw new Exception("Run should be in 0 - 6 range");
        }
        isPlayed = true;
        runsScored.add(n);
        if (n == 4) totalBoundaries++;
        if (n == 6) totalSixes++;
    }
}
