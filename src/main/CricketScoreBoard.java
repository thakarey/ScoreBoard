package main;

import main.cache.Cache;
import main.model.Ball;
import main.Enum.BallType;
import main.Enum.RunType;
import main.player.MatchBetween;
import main.player.Player;
import main.player.PlayingPlayers;
import main.model.Team;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static main.cache.Cache.matchMap;

//Driver Class
public class CricketScoreBoard {
    public static int playerCount = 1;
    public static Team team1;
    public static Team team2;
    public static MatchBetween matchBetween;
    public static Match match;

    public static void main(String[] args) {
        /*
        1. Creating Match
        2. Initializing Teams
        3. Adding Players and there order in each team
        4. For Maintaining a batting order - playingOrder in Team Class
         */

        // How Create Test Case;
        // 3 Test Cases are attached for reference
        // Crate Class of Scorer
        // give string[][] of n overs
        // call setupMatch function (noOfPlayer,totalOvers,scorer,firstInnings score, secondInnings score, matchName)
        // match Name should be unique for each test Case.

        //1st Example
        Scorer s1 = new Scorer();
        String[][] arr = new String[][]{{
                "1", "1", "1", "1", "1", "2"},
                {"W", "4", "4", "Wd", "W", "1", "6"}};

        String[][] arr1 = new String[][]{{
                "4", "6", "W", "W", "1", "1"},
                {"6", "1", "W", "W"}};
        setupMatch(5, arr.length, s1, arr, arr1,"match1");


        /*
         *
         * Example 2 - Uncomment Below Code
         *
        */
//        Scorer s2 = new Scorer();
//        String[][] arr3 = new String[][]{{
//                "1", "1", "1", "1", "1", "2"},
//                {"W", "4", "4", "Wd", "W", "1", "6"},
//                {"1", "W","W"}};
//
//        String[][] arr4 = new String[][]{{
//                "4", "6", "2", "W", "1", "1"},
//                {"6", "1", "W", "2","1","3"},
//                {"W", "4", "4", "Wd", "1", "1"}
//        };
//        setupMatch(5, arr1.length, s2, arr3, arr4,"match2");



        /*
        *
        * Example 3 - Uncomment Below Code
        *
        */

//        Scorer s3 = new Scorer();
//        String[][] arr5 = new String[][]{{
//                "1", "1", "1", "1", "1", "2"},
//                {"W", "4", "4", "Wd", "W", "1", "1"}};
//
//        String[][] arr6 = new String[][]{{
//                "4", "6", "W", "W", "1", "1"},
//                {"6", "1", "W", "W"}};
//        setupMatch(5, arr5.length, s3, arr5, arr6,"match3");
    }

    /*
           1. Creating Match
           2. Initializing Teams
           3. Adding Players and there order in each team
    */
    public static void setupMatch(int noOfPlayers, int noOfOvers, Scorer s1, String[][] arr, String[][] arr1,String matchName) {
        team1 = new Team("T1");
        team2 = new Team("T2");

        matchBetween = new MatchBetween(team1, team2);
        // Add Players in Each Team
        addPlayersToTeam(team1, noOfPlayers);
        addPlayersToTeam(team2, noOfPlayers);


        match = new Match(matchBetween);
        matchMap.put(matchName, match);

        int inning = 1;
        //first Inning
        playInning(arr, inning, s1, team1, matchName);

        //increasing inningCount
        inning++;

        //second Inning
        playInning(arr1, inning, s1, team2, matchName);

        // Announce Winner
        s1.announceWinner(matchName);
    }

    private static void playInning(String[][] arr, int inning, Scorer s1, Team team, String matchName) {
        Player one = team.getNextPlayer();
        Player two = team.getNextPlayer();
        Cache.playingPlayers = new PlayingPlayers(one, two);

        //Converting inputs into List of balls
        for (int i = 1; i <= arr.length; i++) {
            List<Ball> balls = convertStringToBalls(i, arr[i - 1]);
            for (Ball b : balls) {
                s1.setScore(b, matchName, inning);
            }
            s1.printScore(matchName, inning);
        }
    }

    public static List<Ball> convertStringToBalls(int ithOver, String[] arr) {
        List<Ball> balls = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            Ball ball = null;
            ball = new Ball(ithOver, i + 1);
            if (arr[i].length() == 1 && Character.isDigit(arr[i].charAt(0))) {
                int tmp = Integer.parseInt(arr[i]);
                ball.setBallType(BallType.BALL);
                RunType runType = getRunType(tmp);
                ball.setRunType(runType);
            } else {
                if (arr[i].startsWith("Wd")) {
                    ball.setBallType(BallType.WIDE);
                    ball.setRunType(RunType.WIDE);
                } else if (arr[i].startsWith("N")) {
                    ball.setBallType(BallType.NO_BALL);
                    ball.setRunType(RunType.NO_BALL_ZERO);
                } else {
                    ball.setBallType(BallType.WICKET);
                    ball.setRunType(RunType.ZERO);
                }
            }
            balls.add(ball);
        }
        return balls;
    }

    private static RunType getRunType(int tmp) {
        if (tmp == 0) return RunType.ZERO;
        if (tmp == 1) return RunType.ONE;
        if (tmp == 2) return RunType.TWO;
        if (tmp == 3) return RunType.THREE;
        if (tmp == 4) return RunType.FOUR;
        if (tmp == 6) return RunType.SIX;
        return null;
    }

    /*
        1. Playing Order is Queue.
        2. Once Wicket is down, next batsman -> poll it from queue.
     */
    private static void addPlayersToTeam(Team team, int noOfPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            Player player = new Player("P" + playerCount++);
            players.add(player);
        }
        team.setPlayers(players);
        team.setPlayingOrder(new LinkedList<>(players));
    }
}
