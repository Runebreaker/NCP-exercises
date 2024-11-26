package ex6;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Task3 {
    public static void main(String[] args) {
        final int TIME_BETWEEN_TICKS = 16;
        CountDownLatch startLatch = new CountDownLatch(3);
        CountDownLatch endLatch = new CountDownLatch(3);

        Thread[] subsystems = {
                new RandomWaitingThread("Physics", startLatch, endLatch, TIME_BETWEEN_TICKS),
                new RandomWaitingThread("Audio", startLatch, endLatch, TIME_BETWEEN_TICKS),
                new RandomWaitingThread("Graphics", startLatch, endLatch, TIME_BETWEEN_TICKS)
        };

        Thread gameLogicThread = new GameLogicThread(subsystems, startLatch, TIME_BETWEEN_TICKS);

        gameLogicThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            gameLogicThread.interrupt();
            System.out.println("Game Logic thread is shutting down...");
        }

        try {
            System.out.println("Waiting for subsystems to finish...");
            // Wait for subsystems to finish and join
            endLatch.await();
            for (var subsystem : subsystems) {
                subsystem.join();
            }
            System.out.println("All subsystems have finished!");
            System.out.println("Engine is shutting down...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GameLogicThread extends Thread {
    private final Thread[] subsystems;
    private final CountDownLatch startLatch;
    private final int timeBetweenTicks;

    public GameLogicThread(Thread[] subsystems, CountDownLatch startLatch, int timeBetweenTicks) {
        this.subsystems = subsystems;
        this.startLatch = startLatch;
        this.timeBetweenTicks = timeBetweenTicks;
    }

    @Override
    public void run() {
        System.out.println("Engine is starting...");
        for (Thread subsystem : subsystems) {
            subsystem.start();
        }
        try {
            // Wait for all subsystems to be ready
            startLatch.await();
            System.out.println("All subsystems are ready, starting game loop...");
            while (!isInterrupted()) {
                Thread.sleep(timeBetweenTicks);
                System.out.println("Tick");
            }
        } catch (InterruptedException e) {
            for (var subsystem : subsystems) {
                subsystem.interrupt();
            }
            interrupt();
        }
    }
}

class RandomWaitingThread extends Thread {
    private final Random random = new Random();
    private final String name;
    private final CountDownLatch startLatch;
    private final CountDownLatch endLatch;
    private final int timeBetweenTicks;

    public RandomWaitingThread(String name, CountDownLatch startLatch, CountDownLatch endLatch, int timeBetweenTicks) {
        this.name = name;
        this.startLatch = startLatch;
        this.endLatch = endLatch;
        this.timeBetweenTicks = timeBetweenTicks;
    }

    @Override
    public void run() {
        System.out.println("Subsystem " + name + " is initializing...");
        try {
            init();
            // Wait for all other subsystems to be ready
            startLatch.await();
            while (!isInterrupted()) {
                Thread.sleep(timeBetweenTicks);
                System.out.println("Updating " + name + "...");
            }
        } catch (InterruptedException e) {
            System.out.println("Subsystem " + name + " was interrupted and is shutting down");
            interrupt();
        } finally {
            exit();
        }
    }

    private void exit() {
        endLatch.countDown();
        System.out.println("Subsystem " + name + " has finished!");
    }

    private void init() throws InterruptedException {
        Thread.sleep(random.nextLong(500));
        System.out.println("Subsystem " + name + " is ready");
        startLatch.countDown();
    }
}
