package main.model;

import lombok.Getter;
import lombok.Setter;
import main.player.PlayerScore;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains ALl overs of Given Innings along with Players Score
 */
@Getter
@Setter
public class Innings {
    private int inningsNumber;
    private LinkedHashMap<Integer, Over> overs;
    private Map<String, PlayerScore> playerScores = new HashMap<>();

    public Innings(int inningsNumber) {
        this.inningsNumber = inningsNumber;
        overs = new LinkedHashMap<>();
    }
}

