package ex1;

class HelloWorldThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}

class HelloWorldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}

public class Task1 {
    public static void main(String[] args) {
        // Solution 1: Via extended Thread class
        Thread hw1 = new HelloWorldThread();

        // Solution 2: Via Runnable
        Runnable runnable = new HelloWorldRunnable();
        Thread hw2 = new Thread(runnable);

        // Solution 3: Via anonymous Runnable
        Runnable anonymousRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        };
        Thread hw3 = new Thread(anonymousRunnable);

        // Solution 4: Via lambda
        Thread hw4 = new Thread(() -> System.out.println("Hello World!"));

        // Run them
        hw1.start();
        hw2.start();
        hw3.start();
        hw4.start();
    }
}