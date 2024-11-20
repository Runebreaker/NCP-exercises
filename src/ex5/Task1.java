package ex5;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private GameState.Player currentTurn = GameState.Player.Player1;

    public void setGameEnd() {
        currentTurn = GameState.Player.NO_PLAYER;
    }

    public void setGameStart() {
        currentTurn = GameState.Player.Player1;
    }

    public boolean isPlayerTurn(GameState.Player player) {
        return currentTurn == player;
    }

    public void setNextPlayer() {
        switch (currentTurn) {
            case Player1:
                currentTurn = GameState.Player.Player2;
                break;
            case Player2:
                currentTurn = GameState.Player.Player3;
                break;
            case Player3:
                currentTurn = GameState.Player.Player4;
                break;
            case Player4:
                currentTurn = GameState.Player.Player5;
                break;
            case Player5:
                currentTurn = GameState.Player.Player6;
                break;
            case Player6:
                currentTurn = GameState.Player.Player1;
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
    private Lock lock;
    private Condition playerTurnCondition;

    public PlayerThread(GameState gameState, GameState.Player player, long playTime, Lock lock, Condition playerTurnCondition) {
        this.gameState = gameState;
        this.player = player;
        this.playTime = playTime;
        this.lock = lock;
        this.playerTurnCondition = playerTurnCondition;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            lock.lock();
            try {
                while (!gameState.isPlayerTurn(player)) {
                    System.out.println("Player " + player.name() + " tried to play and is now waiting...");
                    TOTAL_TIME_NOT_TURN++;
                    playerTurnCondition.await();
                }
                System.out.println("Player " + player.name() + " is playing...");
                sleep(playTime);
                gameState.setNextPlayer();
                playerTurnCondition.signalAll();
            } catch (InterruptedException e) {
                interrupt();
            } finally {
                lock.unlock();
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
        var lock = new ReentrantLock();
        var playerTurnCondition = lock.newCondition();

        var playerThreads = new ArrayList<PlayerThread>() {
            {
                add(new PlayerThread(gameState, GameState.Player.Player1, playTimePerPlayerInMs, lock, playerTurnCondition));
                add(new PlayerThread(gameState, GameState.Player.Player2, playTimePerPlayerInMs, lock, playerTurnCondition));
                add(new PlayerThread(gameState, GameState.Player.Player3, playTimePerPlayerInMs, lock, playerTurnCondition));
                add(new PlayerThread(gameState, GameState.Player.Player4, playTimePerPlayerInMs, lock, playerTurnCondition));
                add(new PlayerThread(gameState, GameState.Player.Player5, playTimePerPlayerInMs, lock, playerTurnCondition));
                add(new PlayerThread(gameState, GameState.Player.Player6, playTimePerPlayerInMs, lock, playerTurnCondition));
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
        System.out.println("Total time thread active but not their turn: " + PlayerThread.TOTAL_TIME_NOT_TURN);
    }
}
