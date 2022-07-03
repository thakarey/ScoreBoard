package main.player;


import lombok.Data;

// Batsman on the field playing
@Data
public class PlayingPlayers {

    Player player1;
    Player player2;
    Player strikePlayer;

    public PlayingPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        strikePlayer = player1;
    }

    public Player changeAfterWicket(Player newPlayer) {
        Player strikePlayer = getStrikePlayer();
        if (strikePlayer.getName().equals(player1.getName())) {
            player1 = newPlayer;
            this.strikePlayer = player1;
        } else {
            this.strikePlayer = player2;
            player2 = newPlayer;
        }
        return this.strikePlayer;
    }

    //ChangeStrike now p2 is on strike
    public Player changeStrike() {
        strikePlayer = getStrikePlayer();
        if (strikePlayer.getName().equals(player1.getName())) {
            strikePlayer = player2;
        } else {
            strikePlayer = player1;
        }
        return strikePlayer;
    }

    //Get Player on Strike
    public Player getStrikePlayer() {
        return strikePlayer;
    }
}
