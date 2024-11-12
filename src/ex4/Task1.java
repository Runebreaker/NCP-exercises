package ex4;

import java.util.ArrayList;

class GameState {

    enum Player {
        NO_PLAYER,
        Player1,
        Player2,
        Player3,
        Player4,
        Player5,
        Player6
    }

    private Player currentTurn = Player.Player1;

    public void setGameEnd() {
        currentTurn = Player.NO_PLAYER;
    }

    public void setGameStart() {
        currentTurn = Player.Player1;
    }

    public boolean isPlayerTurn(Player player) {
        return currentTurn == player;
    }

    public void setNextPlayer() {
        switch (currentTurn) {
            case Player1:
                currentTurn = Player.Player2;
                break;
            case Player2:
                currentTurn = Player.Player3;
                break;
            case Player3:
                currentTurn = Player.Player4;
                break;
            case Player4:
                currentTurn = Player.Player5;
                break;
            case Player5:
                currentTurn = Player.Player6;
                break;
            case Player6:
                currentTurn = Player.Player1;
                break;
            default:
                System.out.println("problem unknown player turn ID");
        }
    }
}

class PlayerThread extends Thread {
    public static volatile long TOTAL_TIME_NOT_TURN; // just for profiling
    private GameState gameState;
    private GameState.Player player;
    private long playTime;

    public PlayerThread(GameState gameState, GameState.Player player, long playTime) {
        this.gameState = gameState;
        this.player = player;
        this.playTime = playTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                synchronized (gameState) {
                    while (!gameState.isPlayerTurn(player)) {
                        TOTAL_TIME_NOT_TURN++;
                        gameState.wait();
                    }
                    sleep(playTime);
                    gameState.setNextPlayer();
                    gameState.notifyAll();
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        var gameState = new GameState();
        var overallPlayingTimeInMs = 1000;
        var numberOfPlayer = 6;
        var percentageOftimePerPlayer = 50f;
        long playTimePerPlayerInMs = (long) (((float) overallPlayingTimeInMs / numberOfPlayer) * (percentageOftimePerPlayer / 100));

        var playerThreads = new ArrayList<PlayerThread>() {
            {
                add(new PlayerThread(gameState, GameState.Player.Player1, playTimePerPlayerInMs));
                add(new PlayerThread(gameState, GameState.Player.Player2, playTimePerPlayerInMs));
                add(new PlayerThread(gameState, GameState.Player.Player3, playTimePerPlayerInMs));
                add(new PlayerThread(gameState, GameState.Player.Player4, playTimePerPlayerInMs));
                add(new PlayerThread(gameState, GameState.Player.Player5, playTimePerPlayerInMs));
                add(new PlayerThread(gameState, GameState.Player.Player6, playTimePerPlayerInMs));
            }
        };

        playerThreads.forEach(p -> p.start());
        gameState.setGameStart();
        Thread.sleep(overallPlayingTimeInMs);
        gameState.setGameEnd();
        playerThreads.forEach(p -> p.interrupt());
        playerThreads.forEach(p -> {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Total time thread active but not their turn " + PlayerThread.TOTAL_TIME_NOT_TURN);
    }
}
