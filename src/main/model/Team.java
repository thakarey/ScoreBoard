package main.model;

import lombok.Data;
import main.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Data
public class Team {
    private final String name;
    private List<Player> players;
    //Maintaining a queue to getNextPlayer after a wicket
    private Queue<Player> playingOrder;

    public Team(String name) {
        this.name = name;
        players = new ArrayList<>();
    }

    public Player getNextPlayer(){
        if(playingOrder.isEmpty()){
            return null;
        }
        return playingOrder.poll();
    }
}
