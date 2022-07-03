package main;

import lombok.Data;
import main.model.Innings;
import main.player.MatchBetween;

import java.util.HashMap;
import java.util.Map;

@Data
public class Match {

    private final MatchBetween matchBetween;
    private Map<Integer, Innings> innings;

    public Match(MatchBetween matchBetween) {
        this.matchBetween = matchBetween;
        innings = new HashMap<>();

    }
}
