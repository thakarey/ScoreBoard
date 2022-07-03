package main.player;

import lombok.Data;
import main.model.Team;

@Data
public class MatchBetween {
    Team team1;
    Team team2;

    public MatchBetween(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
}
