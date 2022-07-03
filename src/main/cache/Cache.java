package main.cache;

import main.Match;
import main.Scorecard;
import main.player.PlayingPlayers;

import java.util.HashMap;
import java.util.Map;

/**
 * Caching The ScoreCard for each Innings and Playing players - batsman on the field.
 */
public class Cache {
        public static Map<String, Match> matchMap = new HashMap<>();
        public static Map<String, Map<Integer, Scorecard>> scoreCardMap = new HashMap<>();
        public static PlayingPlayers playingPlayers;
}
