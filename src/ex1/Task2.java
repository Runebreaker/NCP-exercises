package ex1;

import java.util.Random;

class ThreadWithID extends Thread {
    private int id;
    public void setId(int newId) {
        id = newId;
    }
    ThreadWithID(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hello, World! -  From New Thread - Application ID [" + id + "]  (JVM ID [" + Thread.currentThread().getId() + "])");
        int sleepTime = new Random().nextInt(5000) + 1;
        System.out.println("Thread AppID [" + id + "] will sleep for " + sleepTime + " milli-seconds (random - max 5 seconds)");
        long timeStart = System.currentTimeMillis();
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long actualSleepTime = System.currentTimeMillis() - timeStart;
        System.out.println("Thread AppID [" + id + "] Finished sleeping (actual time sleeping : " + actualSleepTime + " ms)");
    }
}

public class Task2 {
    public static void main(String[] args) {
        System.out.println(">> Main Thread Starting...\n");
        System.out.println("Hello, World! - from Main Thread JVM name : " + Thread.currentThread().getName() + "\n");

        Thread[] threads = {
                new ThreadWithID(0),
                new ThreadWithID(1),
                new ThreadWithID(2)
        };

        System.out.println("3 New Threads Created :\n");
        for (int i = 0; i < threads.length; i++) {
            System.out.println("AppID [" + i + "] JVM name: " + threads[i].getName());
        }
        System.out.println("\n");

        System.out.println(">> New Threads Starting...\n");

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n");

        System.out.println("<< All New Threads Finished\n");
        System.out.println("<< Main Thread Finished");
    }
}